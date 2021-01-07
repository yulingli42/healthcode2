package com.healthcode.service;

import com.healthcode.domain.Teacher;
import org.jetbrains.annotations.Nullable;

/**
 * @author zhenghong
 */
public interface ITeacherService {
    /**
     * 教师登录处理
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回教师信息，否则返回 null
     */
    @Nullable
    Teacher login(String username, String password);
}
