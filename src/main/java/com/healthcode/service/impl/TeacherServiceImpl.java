package com.healthcode.service.impl;

import com.google.common.collect.Lists;
import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.TeacherDailyCardDao;
import com.healthcode.dao.TeacherDao;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.StudentDailyCard;
import com.healthcode.domain.Teacher;
import com.healthcode.domain.TeacherDailyCard;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.ITeacherService;
import com.healthcode.vo.TeacherDailyCardStatistic;
import com.healthcode.vo.TeacherDailyCardVo;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class TeacherServiceImpl implements ITeacherService {
    private final TeacherDao teacherDao = new TeacherDao();
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
        // TODO
        TeacherDailyCardStatistic statistic = new TeacherDailyCardStatistic();
        statistic.setTotalTeacherCount(100);
        statistic.setGreenCodeTeacherCount(80);
        statistic.setYellowCodeTeacherCount(10);
        statistic.setRedCodeTeacherCount(5);
        ArrayList<TeacherDailyCardVo> list = Lists.newArrayList();
        TeacherDailyCardVo cardVo = new TeacherDailyCardVo();
        cardVo.setName("test");
        cardVo.setClassName("test");
        cardVo.setType(HealthCodeType.GREEN);
        cardVo.setTeacherId(1);
        for (int i = 0; i < 100; i++) {
            list.add(cardVo);
        }
        statistic.setDailyCardList(list);

        return statistic;
    }

    @Override
    public void insertTeacher(String teacherId, String name, String idCard, Integer collegeId) {
        //TODO
        System.out.println(teacherId);
        System.out.println(name);
        System.out.println(idCard);
        System.out.println(collegeId);
    }

    @Override
    public byte[] showHealthCode(Teacher teacher) {
        //返回学生二维码
        TeacherDailyCard teacherDailyCard = teacherDailyCardDao.getToDayCardByTeacherID(teacher.getId());
        String content = "姓名:" + teacher.getName() + "\n工号:" + teacher.getId() + "\n健康码类型:" + teacherDailyCard.getResult() + "\n创建时间:" + teacherDailyCard.getCreateTime();
        try {
            return qrCodeService.getHealthCodeBytes(teacherDailyCard.getResult(), content, null, true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
