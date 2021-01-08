package com.healthcode.service.impl;

import com.google.common.collect.Lists;
import com.healthcode.domain.College;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Teacher;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.ITeacherService;
import com.healthcode.vo.TeacherDailyCardStatistic;
import com.healthcode.vo.TeacherDailyCardVo;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class TeacherServiceImpl implements ITeacherService {
    @Override
    public @Nullable Teacher login(String username, String password) {
        //TODO 登录处理

        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("test");
        teacher.setPassword("123456");
        teacher.setIdCard("123456");
        College college = new College();
        college.setId(1);
        college.setName("计算机学院");
        teacher.setCollege(college);
        return teacher;
    }

    @Override
    public Boolean getDailyCardStatus(Teacher teacher) {
        //TODO
        return Math.random() % 2 == 0;
    }

    @Override
    public void submitDailyCard(Teacher teacher, CurrentDailyCard card) {
        //TODO
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
}
