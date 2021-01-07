package com.healthcode.service;

import com.healthcode.domain.Student;
import org.jetbrains.annotations.Nullable;

/**
 * @author zhenghong
 */
public interface IStudentService {
    /**
     * 学生登录处理
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回学生信息，否则返回 null
     */
    @Nullable
    Student login(String username, String password);
}
