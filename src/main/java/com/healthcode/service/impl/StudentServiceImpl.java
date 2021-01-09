package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.StudentDailyCardDao;
import com.healthcode.dao.StudentDao;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Student;
import com.healthcode.domain.StudentDailyCard;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.IStudentService;
import com.healthcode.vo.StudentDailyCardStatistic;
import com.healthcode.vo.StudentDailyCardVo;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
        //TODO

        StudentDailyCardStatistic statistic = new StudentDailyCardStatistic();
        statistic.setTotalStudentCount(100);
        statistic.setGreenCodeStudentCount(60);
        statistic.setYellowCodeStudentCount(30);
        statistic.setRedCodeStudentCount(5);
        List<StudentDailyCardVo> list = new ArrayList<>();
        StudentDailyCardVo vo = new StudentDailyCardVo();
        vo.setStudentId("0001");
        vo.setName("test");
        vo.setClassName("test");
        vo.setMajorName("test");
        vo.setCollegeName("test");
        vo.setType(HealthCodeType.GREEN);
        for (int i = 0; i < 100; i++) {
            list.add(vo);
        }
        statistic.setDailyCardList(list);
        return statistic;
    }

    @Override
    public void insertStudent(String id, String name, Integer classId, String idCard) {
        //TODO
        System.out.println(id + name + classId + idCard);
    }

    @Override
    public byte[] showHealthCode(Student student) {
        //返回学生二维码
        StudentDailyCard studentDailyCard = studentDailyCardDao.getToDayCardByStudentID(student.getId());
        String content = "姓名:" + student.getName() + "\n学号:" + student.getId() + "\n健康码类型:" + studentDailyCard.getResult() + "\n创建时间:" + studentDailyCard.getCreateTime();
        try {
            return qrCodeService.getHealthCodeBytes(studentDailyCard.getResult(), content, null, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void submitDailyCard(Student student, CurrentDailyCard card) {
        //判断健康码类型
        HealthCodeType healthCodeType = qrCodeService.judgeQRCodeType(card);
        //提交至数据库
        studentDao.submitDailyCard(student, healthCodeType);
    }
}
