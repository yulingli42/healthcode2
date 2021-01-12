package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.CollegeDao;
import com.healthcode.dao.TeacherDailyCardDao;
import com.healthcode.dao.TeacherDao;
import com.healthcode.domain.College;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Teacher;
import com.healthcode.domain.TeacherDailyCard;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.ITeacherService;
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
        //教师登录
        Teacher teacher = teacherDao.getByUsername(username);
        if(Objects.isNull(username) || !teacher.getPassword().equals(password)){
            throw new HealthCodeException("用户名密码错误");
        }
        return teacher;
    }

    @Override
    public Boolean getDailyCardStatus(Teacher teacher) {
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
        //添加教师
        //密码默认为身份证后六位
        teacherDao.insert(teacherId, name, idCard.substring(idCard.length() - 6), collegeId, idCard);
    }

    @Override
    public byte[] showHealthCode(Teacher teacher) {
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
        //将Excel文件中的教师数据导入数据库
        try {
            List<Object> teacherData = ExcelUtil.readLessThan1000RowBySheet(filePart.getInputStream(), null);

            for(int i = 1;i < teacherData.size();i++){
                @SuppressWarnings("unchecked")
                ArrayList<String> list = (ArrayList<String>)teacherData.get(i);
                System.out.println(list);
                String id = list.get(0);
                String name = list.get(1);
                String collegeName = list.get(2);
                String idCard = list.get(3);

                if(id == null || name == null || collegeName == null || idCard == null){
                    continue;
                }
                //获取教室号
                Integer collegeId = collegeDao.getCollegeIdByName(collegeName);
                //插入教师信息
                insertTeacher(id, name, idCard, collegeId);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
