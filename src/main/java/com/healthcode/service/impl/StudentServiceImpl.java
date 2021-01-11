package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.StudentDailyCardDao;
import com.healthcode.dao.StudentDao;
import com.healthcode.domain.Clazz;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Student;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.IStudentService;
import com.healthcode.utils.JudgeHealthCodeTypeUtil;
import com.healthcode.vo.StudentDailyCardStatistic;
import com.healthcode.vo.StudentDailyCardVo;
import org.jetbrains.annotations.Nullable;

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
    private final StudentDailyCardDao studentDailyCardDao = new StudentDailyCardDao();

    private final QRCodeServiceImpl qrCodeService = new QRCodeServiceImpl();

    @Override
    public Student login(String username, String password) {
        //学生登录
        Student student = studentDao.getByUsername(username);
        if (Objects.isNull(student) || !student.getPassword().equals(password)) {
            throw new HealthCodeException("用户名密码错误");
        }
        return student;
    }

    @Override
    public Boolean checkStudentDailyCardToday(Student student) {
        //true:已打卡 false:未打卡
        return studentDailyCardDao.getToDayCardByStudentID(student.getId()) != null;
    }

    @Override
    public StudentDailyCardStatistic getStudentStatistic(
            @Nullable Integer clazzId,
            @Nullable Integer majorId,
            @Nullable Integer collegeId) {
        StudentDailyCardStatistic statistic = new StudentDailyCardStatistic();
        List<Student> students = studentDao.listAll();
        List<StudentDailyCardVo> studentDailyCardVos = new ArrayList<>();

        int greenCodeCount = 0, yellowCodeCount = 0, redCodeCount = 0;
        for (Student student:students) {
            //当天的打卡健康码情况
            //StudentDailyCard studentDailyCard = studentDailyCardDao.getToDayCardByStudentID(student.getId());
            //实际上的健康码情况
            List<HealthCodeType> healthCodeTypeList = studentDailyCardDao.judgeHealthCodeTypeByPast(student.getId());
            HealthCodeType healthCodeType = JudgeHealthCodeTypeUtil.judgeHealthTypeHelper(healthCodeTypeList);

            StudentDailyCardVo studentDailyCardVo = new StudentDailyCardVo();

            studentDailyCardVo.setStudentId(student.getId());
            studentDailyCardVo.setName(student.getName());
            Clazz clazz = student.getClazz();
            studentDailyCardVo.setClassName(clazz.getName());
            studentDailyCardVo.setMajorName(clazz.getMajor().getName());
            studentDailyCardVo.setCollegeName(clazz.getMajor().getCollege().getName());
            studentDailyCardVo.setType(healthCodeType);

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
        //添加学生
        //密码默认为身份证后六位
        studentDao.insert(id, name, idCard.substring(idCard.length() - 6), classId, idCard);
    }

    @Override
    public byte[] showHealthCode(Student student) {
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
        //判断当前健康状态
        HealthCodeType healthCodeType = qrCodeService.judgeQRCodeType(card);
        //提交至数据库
        studentDao.submitDailyCard(student, healthCodeType);
    }
}
