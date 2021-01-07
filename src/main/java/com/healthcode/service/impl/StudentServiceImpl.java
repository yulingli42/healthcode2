package com.healthcode.service.impl;

import com.healthcode.dao.StudentDao;
import com.healthcode.domain.Student;
import com.healthcode.service.IStudentService;

import java.util.Objects;

public class StudentServiceImpl implements IStudentService {
    private final StudentDao studentDao = new StudentDao();

    @Override
    public Student login(String username, String password) {
        Student student = studentDao.getByUsername(username);
        if (Objects.isNull(student) || !student.getPassword().equals(password)) {
            return null;
        }
        return student;
    }
}
