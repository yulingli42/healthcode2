package com.healthcode.service;

import com.healthcode.domain.Admin;
import org.jetbrains.annotations.Nullable;

/**
 * @author qianlei
 */
public interface IAdminService {
    /**
     * 管理员登录处理
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆成功返回管理员信息，否则返回 null
     */
    @Nullable
    Admin login(String username, String password);

}
