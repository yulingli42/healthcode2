package com.healthcode.service.impl;

import com.google.common.collect.Lists;
import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.AdminDao;
import com.healthcode.domain.Admin;
import com.healthcode.service.IAdminService;

import java.util.List;
import java.util.Objects;

/**
 * @author qianlei zhenghong
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

    @Override
    public void insertAdmin(String username, String password, Admin.AdminRole adminRole, Integer collegeId) {
        adminDao.insert(username, password, adminRole, collegeId);
    }

    @Override
    public void alterAdmin(String username, String newUsername, String password, Admin.AdminRole adminRole, int collegeId) {
        adminDao.alter(username, newUsername, password, adminRole, collegeId);
    }

    @Override
    public List<Admin> findAll() {
        //TODO
        return Lists.newArrayList();
    }
}
