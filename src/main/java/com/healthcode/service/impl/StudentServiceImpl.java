package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.StudentDao;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Student;
import com.healthcode.dto.CurrentDailyCard;
import com.healthcode.service.IStudentService;
import com.healthcode.vo.StudentDailyCardStatistic;
import com.healthcode.vo.StudentDailyCardVo;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author qianlei
 */
public class StudentServiceImpl implements IStudentService {
    private final StudentDao studentDao = new StudentDao();

    @Override
    public Student login(String username, String password) {
        //TODO
        Student student = studentDao.getByUsername(username);
        if (Objects.isNull(student) || !student.getPassword().equals(password)) {
            throw new HealthCodeException("用户名密码错误");
        }
        return student;
    }

    @Override
    public Boolean checkStudentDailyCardToday(Student student) {
        //TODO

        return new Random().nextInt() % 2 == 0;
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
    public void submitDailyCard(Student student, CurrentDailyCard card) {
        //TODO
    }
}
