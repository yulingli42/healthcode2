package com.healthcode.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.ClazzDao;
import com.healthcode.dao.StudentDailyCardDao;
import com.healthcode.dao.StudentDao;
import com.healthcode.domain.Clazz;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Student;
import com.healthcode.domain.StudentDailyCard;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.IStudentService;
import com.healthcode.utils.CheckValueUtil;
import com.healthcode.utils.ExcelUtil;
import com.healthcode.utils.JudgeHealthCodeTypeUtil;
import com.healthcode.vo.StudentDailyCardStatistic;
import com.healthcode.vo.StudentDailyCardVo;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.Part;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author qianlei zhenghong
 */
public class StudentServiceImpl implements IStudentService {
    private final StudentDao studentDao = new StudentDao();
    private final ClazzDao clazzDao = new ClazzDao();
    private final StudentDailyCardDao studentDailyCardDao = new StudentDailyCardDao();

    private final QRCodeServiceImpl qrCodeService = new QRCodeServiceImpl();

    @Override
    public Student login(String username, String password) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(username, password)) {
            throw new HealthCodeException("信息不可为空");
        }
        //学生登录
        Student student = studentDao.getByUsername(username);
        if (Objects.isNull(student) || !student.getPassword().equals(password)) {
            throw new HealthCodeException("用户名密码错误");
        }
        return student;
    }

    @Override
    public Boolean checkStudentDailyCardToday(Student student) {
        //校验数据
        if (Objects.isNull(student)) {
            throw new HealthCodeException("当前学生不存在");
        }
        //true:已打卡 false:未打卡
        return studentDailyCardDao.getToDayCardByStudentID(student.getId()) != null;
    }

    @Override
    public StudentDailyCardStatistic getStudentStatistic(
            @Nullable Integer clazzId,
            @Nullable Integer majorId,
            @Nullable Integer collegeId) {
        StudentDailyCardStatistic statistic = new StudentDailyCardStatistic();
        List<Student> students;

        if (!Objects.isNull(clazzId)) {
            students = studentDao.listAllByClazzId(clazzId);
        } else if (!Objects.isNull(majorId)) {
            students = studentDao.listAllByMajorId(majorId);
        } else if (!Objects.isNull(collegeId)) {
            students = studentDao.listAllByCollegeId(collegeId);
        } else {
            students = studentDao.listAll();
        }

        List<StudentDailyCardVo> studentDailyCardVos = new ArrayList<>();

        int greenCodeCount = 0, yellowCodeCount = 0, redCodeCount = 0;
        for (Student student : students) {

            StudentDailyCardVo studentDailyCardVo = new StudentDailyCardVo();

            //当天的打卡健康码情况
            StudentDailyCard studentDailyCard = studentDailyCardDao.getToDayCardByStudentID(student.getId());
            studentDailyCardVo.setHadSubmitDailyCard(studentDailyCard != null);

            //实际上的健康码情况
            List<HealthCodeType> healthCodeTypeList = studentDailyCardDao.judgeHealthCodeTypeByPast(student.getId());
            HealthCodeType healthCodeType = JudgeHealthCodeTypeUtil.judgeHealthTypeHelper(healthCodeTypeList);

            studentDailyCardVo.setType(healthCodeType);
            studentDailyCardVo.setStudentId(student.getId());
            studentDailyCardVo.setName(student.getName());
            Clazz clazz = student.getClazz();
            studentDailyCardVo.setClassName(clazz.getName());
            studentDailyCardVo.setMajorName(clazz.getMajor().getName());
            studentDailyCardVo.setCollegeName(clazz.getMajor().getCollege().getName());

            switch (healthCodeType) {
                case RED:
                    ++redCodeCount;
                    break;
                case GREEN:
                    ++greenCodeCount;
                    break;
                case YELLOW:
                    ++yellowCodeCount;
            }

            studentDailyCardVos.add(studentDailyCardVo);
        }

        statistic.setTotalStudentCount(students.size());
        statistic.setGreenCodeStudentCount(greenCodeCount);
        statistic.setYellowCodeStudentCount(yellowCodeCount);
        statistic.setRedCodeStudentCount(redCodeCount);
        statistic.setDailyCardList(studentDailyCardVos);

        return statistic;
    }

    @Override
    public void insertStudent(String id, String name, Integer classId, String idCard) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(id, name, idCard)) {
            throw new HealthCodeException("信息不可为空");
        }
        if (Objects.isNull(clazzDao.getById(classId))) {
            throw new HealthCodeException("该班级号不存在");
        }
        if (!IdcardUtil.isValidCard(idCard)) {
            throw new HealthCodeException("身份证无效");
        }
        //添加学生
        //密码默认为身份证后六位
        studentDao.insert(id, name, idCard.substring(idCard.length() - 6), classId, idCard);
    }

    @Override
    public byte[] showHealthCode(Student student) {
        //校验数据
        if (Objects.isNull(student)) {
            throw new HealthCodeException("信息不可为空");
        }
        //返回学生二维码
        List<HealthCodeType> healthCodeTypeList = studentDailyCardDao.judgeHealthCodeTypeByPast(student.getId());
        HealthCodeType healthCodeType = JudgeHealthCodeTypeUtil.judgeHealthTypeHelper(healthCodeTypeList);
        String content = "姓名:" + student.getName() + "\n学号:" + student.getId() + "\n健康码类型:" + healthCodeType + "\n创建时间:" + new Timestamp(new Date().getTime());
        try {
            return qrCodeService.getHealthCodeBytes(healthCodeType, content, null, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void submitDailyCard(Student student, CurrentDailyCard card) {
        //校验数据
        if (Objects.isNull(student) || Objects.isNull(card)) {
            throw new HealthCodeException("信息不可为空");
        }
        //判断当前健康状态
        HealthCodeType healthCodeType = qrCodeService.judgeQRCodeType(card);
        //提交至数据库
        studentDao.submitDailyCard(student, healthCodeType);
    }


    @Override
    public void addStudentFromExcel(Part filePart) {
        //校验数据
        if (Objects.isNull(filePart)) {
            throw new HealthCodeException("导入的文件为空");
        }
        //将Excel文件中的学生数据导入数据库
        try {
            List<Object> studentData = ExcelUtil.readLessThan1000RowBySheet(filePart.getInputStream(), null);

            for (int i = 1; i < studentData.size(); i++) {
                @SuppressWarnings("unchecked")
                ArrayList<String> list = (ArrayList<String>) studentData.get(i);
                System.out.println(list);
                String id = list.get(0);
                String name = list.get(1);
                String className = list.get(2);
                String idCard = list.get(3);

                if (id == null || name == null || className == null || idCard == null) {
                    continue;
                }
                //获取教室号
                Integer classId = clazzDao.getClassIdByName(className);
                //插入学生信息
                insertStudent(id, name, classId, idCard);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new HealthCodeException("导入学生Excel失败");
        }
    }

    @Override
    public void deleteById(String id) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(id)) {
            throw new HealthCodeException("信息不可为空");
        }
        studentDao.delete(id);
    }

    @Override
    public Student getById(String id) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(id)) {
            throw new HealthCodeException("信息不可为空");
        }
        return studentDao.getByUsername(id);
    }

    @Override
    public void updateStudent(String id, String password, Integer classId, String name, String idCard) {
        //校验数据
        if (!CheckValueUtil.checkStringHelper(id)) {
            throw new HealthCodeException("id不可为空");
        }
        //获取当前数据
        Student student = studentDao.getByUsername(id);
        if (!Objects.isNull(student)) {
            name = "".equals(name) ? student.getName() : name;
            password = "".equals(password) ? student.getPassword() : password;
            classId = Objects.isNull(classId) ? student.getClazz().getId() : classId;
            if (!"".equals(idCard) && !IdcardUtil.isValidCard(idCard)) {
                throw new HealthCodeException("身份证无效");
            }
            idCard = "".equals(idCard) ? student.getIdCard() : idCard;
            studentDao.alter(id, name, password, classId, idCard);
        } else {
            throw new HealthCodeException("当前学生不存在");
        }
    }

    @Override
    public void changePassword(String id, String oldPassword, String newPassword) {
        //TODO
    }
}
