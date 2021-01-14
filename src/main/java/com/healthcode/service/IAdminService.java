package com.healthcode.service;

import com.healthcode.domain.Admin;

import java.util.List;

/**
 * @author qianlei zhenghong
 */
public interface IAdminService {
    /**
     * 管理员登录处理
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆成功返回管理员信息，否则返回 null
     */
    Admin login(String username, String password);

    /**
     * 上级管理员添加次级管理员信息
     *
     * @param username  用户名
     * @param password  密码
     * @param adminRole 管理员权限类型
     * @param collegeId 学院号，除院级管理员外为null
     */
    void insertAdmin(String username, String password, Admin.AdminRole adminRole, Integer collegeId);

    /**
     * 修改管理员信息
     */
    void alterAdmin(String username, String newUsername, String password, Admin.AdminRole adminRole, Integer collegeId);

    /**
     * 列出所有管理员
     *
     * @return 所有管理员内容
     */
    List<Admin> findAll();
}
