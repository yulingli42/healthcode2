package com.healthcode.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.CollegeDao;
import com.healthcode.dao.TeacherDailyCardDao;
import com.healthcode.dao.TeacherDao;
import com.healthcode.domain.*;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.ITeacherService;
import com.healthcode.utils.CheckValueUtil;
import com.healthcode.utils.ExcelUtil;
import com.healthcode.utils.JudgeHealthCodeTypeUtil;
import com.healthcode.vo.TeacherDailyCardStatistic;
import com.healthcode.vo.TeacherDailyCardVo;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author zhenghong
 */
public class TeacherServiceImpl implements ITeacherService {
    private final TeacherDao teacherDao = new TeacherDao();
    private final CollegeDao collegeDao = new CollegeDao();
    private final TeacherDailyCardDao teacherDailyCardDao = new TeacherDailyCardDao();

    private final QRCodeServiceImpl qrCodeService = new QRCodeServiceImpl();

    @Override
    public @Nullable Teacher login(String username, String password) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(username, password)){
            throw new HealthCodeException("信息不可为空");
        }
        //教师登录
        Teacher teacher = teacherDao.getByUsername(username);
        if(Objects.isNull(username) || !teacher.getPassword().equals(password)){
            throw new HealthCodeException("用户名密码错误");
        }
        return teacher;
    }

    @Override
    public Boolean getDailyCardStatus(Teacher teacher) {
        //校验数据
        if (Objects.isNull(teacher)){
            throw new HealthCodeException("当前教师不存在");
        }
        //true:已打卡 false:未打卡
        return teacherDailyCardDao.getToDayCardByTeacherID(teacher.getId()) != null;
    }

    @Override
    public void submitDailyCard(Teacher teacher, CurrentDailyCard card) {
        //判断健康码类型
        HealthCodeType healthCodeType = qrCodeService.judgeQRCodeType(card);
        //提交至数据库
        teacherDao.submitDailyCard(teacher, healthCodeType);
    }

    @Override
    public TeacherDailyCardStatistic getTeacherStatistic(@Nullable Integer collegeId) {
        TeacherDailyCardStatistic statistic = new TeacherDailyCardStatistic();
        List<Teacher> teachers;

        if(!Objects.isNull(collegeId)){
            teachers = teacherDao.listAllByCollegeId(collegeId);
        }else {
            teachers = teacherDao.listAll();
        }

        List<TeacherDailyCardVo> teacherDailyCardVos = new ArrayList<>();

        int greenCodeCount = 0, yellowCodeCount = 0, redCodeCount = 0;
        for (Teacher teacher:teachers) {
            TeacherDailyCardVo teacherDailyCardVo = new TeacherDailyCardVo();

            //当天的打卡健康码情况
            TeacherDailyCard teacherDailyCard = teacherDailyCardDao.getToDayCardByTeacherID(teacher.getId());
            teacherDailyCardVo.setHadSubmitDailyCard(teacherDailyCard != null);

            //实际上的健康码情况
            List<HealthCodeType> healthCodeTypeList = teacherDailyCardDao.judgeHealthCodeTypeByPast(teacher.getId());
            HealthCodeType healthCodeType = JudgeHealthCodeTypeUtil.judgeHealthTypeHelper(healthCodeTypeList);

            teacherDailyCardVo.setType(healthCodeType);
            teacherDailyCardVo.setTeacherId(teacher.getId());
            teacherDailyCardVo.setName(teacher.getName());
            College college = teacher.getCollege();
            teacherDailyCardVo.setCollegeName(college.getName());

            switch (healthCodeType){
                case RED:
                    ++redCodeCount;
                    break;
                case GREEN:
                    ++greenCodeCount;
                    break;
                case YELLOW:
                    ++yellowCodeCount;
            }
            teacherDailyCardVos.add(teacherDailyCardVo);
        }

        statistic.setTotalTeacherCount(teachers.size());
        statistic.setGreenCodeTeacherCount(greenCodeCount);
        statistic.setYellowCodeTeacherCount(yellowCodeCount);
        statistic.setRedCodeTeacherCount(redCodeCount);
        statistic.setDailyCardList(teacherDailyCardVos);

        return statistic;
    }

    @Override
    public void insertTeacher(String teacherId, String name, String idCard, Integer collegeId) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(teacherId, name, idCard) || Objects.isNull(collegeId)){
            throw new HealthCodeException("信息不可为空");
        }
        if (Objects.isNull(collegeDao.getById(collegeId))){
            throw new HealthCodeException("该学院号不存在");
        }
        if (!IdcardUtil.isValidCard(idCard)){
            throw new HealthCodeException("身份证无效");
        }
        //添加教师
        //密码默认为身份证后六位
        teacherDao.insert(teacherId, name, idCard.substring(idCard.length() - 6), collegeId, idCard);
    }

    @Override
    public byte[] showHealthCode(Teacher teacher) {
        //校验数据
        if (Objects.isNull(teacher)){
            throw new HealthCodeException("当前教师不存在");
        }
        //返回教师健康码
        List<HealthCodeType> healthCodeTypeList = teacherDailyCardDao.judgeHealthCodeTypeByPast(teacher.getId());
        HealthCodeType healthCodeType = JudgeHealthCodeTypeUtil.judgeHealthTypeHelper(healthCodeTypeList);
        String content = "姓名:" + teacher.getName() + "\n工号:" + teacher.getId() + "\n健康码类型:" + healthCodeType + "\n创建时间:" + new Timestamp(new Date().getTime());
        try {
            return qrCodeService.getHealthCodeBytes(healthCodeType, content, null, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void addTeacherFromExcel(Part filePart) {
        //校验数据
        if (Objects.isNull(filePart)){
            throw new HealthCodeException("导入的文件为空");
        }
        //将Excel文件中的教师数据导入数据库
        try {
            List<Object> teacherData = ExcelUtil.readLessThan1000RowBySheet(filePart.getInputStream(), null);
            List<Teacher> teachers = new ArrayList<>();

            for(int i = 1;i < teacherData.size();i++){
                @SuppressWarnings("unchecked")
                ArrayList<String> list = (ArrayList<String>)teacherData.get(i);
                System.out.println(list);
                String id = list.get(0);
                String name = list.get(1);
                String collegeName = list.get(2);
                String idCard = list.get(3);
                //校验数据
                if(id == null && name == null && collegeName == null && idCard == null){
                    continue;
                }

                //获取学院
                College college = collegeDao.getCollegeByName(collegeName);
                if (Objects.isNull(college)){
                    throw new HealthCodeException("第" + (i + 1) + "行学院不存在，拒绝导入");
                }

                if (!CheckValueUtil.checkStringHelper(id, name, collegeName, idCard)) {
                    throw new HealthCodeException("第" + (i + 1) + "行数据不完整，拒绝导入");
                } else if (!IdcardUtil.isValidCard(idCard)){
                    throw new HealthCodeException("第" + (i + 1) + "行身份证无效，拒绝导入");
                }
                teachers.add(new Teacher(id, name, null, idCard, college));
            }
            System.out.println("开始插入");
            for (Teacher teacher : teachers){
                //插入教师信息
                insertTeacher(teacher.getId(), teacher.getName(), teacher.getIdCard(), teacher.getCollege().getId());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String id) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(id)){
            throw new HealthCodeException("id不可为空");
        }
        teacherDao.delete(id);
    }

    @Override
    public void updateTeacher(String id, String password, Integer collegeId, String name, String idCard) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(id)){
            throw new HealthCodeException("id不可为空");
        }
        //获取当前数据
        Teacher teacher = teacherDao.getByUsername(id);
        if(!Objects.isNull(teacher)){
            name = Objects.isNull(name) || "".equals(name) ? teacher.getName() : name;
            password = Objects.isNull(password) || "".equals(password) ? teacher.getPassword() : password;
            if(Objects.isNull(collegeId) || Objects.isNull(collegeDao.getById(collegeId))){
                collegeId = teacher.getCollege().getId();
            }
            if(!"".equals(idCard) && !IdcardUtil.isValidCard(idCard)){
                throw new HealthCodeException("身份证无效");
            }
            idCard = "".equals(idCard) ? teacher.getIdCard() : idCard;
            teacherDao.alter(id, name, password, collegeId, idCard);
        }else {
            throw new HealthCodeException("当前教师不存在");
        }
    }

    @Override
    public void changePassword(String id, String oldPassword, String newPassword) {
        //校验数据
        if(!CheckValueUtil.checkStringHelper(id, oldPassword, newPassword)){
            throw new HealthCodeException("信息不可为空");
        }
        //验证旧密码
        Teacher teacher = teacherDao.getByUsername(id);
        if(!Objects.isNull(teacher)){
            if(!teacher.getPassword().equals(oldPassword)){
                throw new HealthCodeException("原密码错误");
            }
        }else {
            throw new HealthCodeException("当前教师不存在");
        }
        //修改为新密码
        teacherDao.alterPassword(id, newPassword);
    }
}
