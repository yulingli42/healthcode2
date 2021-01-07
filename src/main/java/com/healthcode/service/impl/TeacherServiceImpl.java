package com.healthcode.service.impl;

import com.healthcode.dao.TeacherDao;
import com.healthcode.domain.Teacher;
import com.healthcode.service.ITeacherService;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TeacherServiceImpl implements ITeacherService {
    private final TeacherDao teacherDao = new TeacherDao();

    @Override
    public @Nullable Teacher login(String username, String password) {
        Teacher teacher = teacherDao.getByUsername(username);
        if (Objects.isNull(teacher) || !teacher.getPassword().equals(password)) {
            return null;
        }
        return teacher;
    }
}
