package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.AdminDao;
import com.healthcode.dao.CollegeDao;
import com.healthcode.domain.Admin;
import com.healthcode.service.IAdminService;
import com.healthcode.utils.CheckValueUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author qianlei zhenghong
 */
public class AdminServiceImpl implements IAdminService {
    private final AdminDao adminDao = new AdminDao();
    private final CollegeDao collegeDao = new CollegeDao();

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
        //校验数据
        if(!CheckValueUtil.checkStringHelper(username, password) || Objects.isNull(adminRole)
                || (Admin.AdminRole.COLLEGE_ADMIN.equals(adminRole) && Objects.isNull(collegeId))){
            throw new HealthCodeException("信息不可为空");
        }
        adminDao.insert(username, password, adminRole, collegeId);
    }

    @Override
    public void alterAdmin(String username, String newUsername, String password, Admin.AdminRole adminRole, Integer collegeId) {
        //校验数据
        if(!CheckValueUtil.checkStringHelper(username)){
            throw new HealthCodeException("用户名不可为空");
        }
        Admin admin = adminDao.getByUsername(username);
        if(!Objects.isNull(admin)){
            newUsername = "".equals(newUsername) ? username : newUsername;
            password = "".equals(password) ? admin.getPassword() : password;
            adminRole = Objects.isNull(adminRole) ? admin.getRole() : adminRole;
            if(Objects.isNull(collegeId) || Objects.isNull(collegeDao.getById(collegeId))){
                collegeId = admin.getCollege().getId();
            }
        }
        adminDao.alter(username, newUsername, password, adminRole, collegeId);
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.listAll();
    }

    @Override
    public void deleteById(Integer id) {
        adminDao.deleteById(id);
    }
}
