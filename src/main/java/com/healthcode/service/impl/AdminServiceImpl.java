package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.AdminDao;
import com.healthcode.domain.Admin;
import com.healthcode.service.IAdminService;

import java.util.Objects;

/**
 * @author qianlei
 */
public class AdminServiceImpl implements IAdminService {
    private final AdminDao adminDao = new AdminDao();

    @Override
    public Admin login(String username, String password) {
        Admin admin = adminDao.getByUsername(username);
        if (Objects.isNull(admin) || !admin.getPassword().equals(password)) {
            throw new HealthCodeException("用户名或密码错误");
        }
        return admin;
    }
}
