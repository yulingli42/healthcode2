package com.healthcode.domain;

import com.healthcode.dao.CollegeDao;

/**
 * @author qianlei
 */
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private AdminRole role;
    private College college;

    public enum AdminRole {
        /**
         * 系统管理员
         */
        SYSTEM_ADMIN("系统管理员"),
        /**
         * 校级管理员
         */
        SCHOOL_ADMIN("校级管理员"),
        /**
         * 院级管理员
         */
        COLLEGE_ADMIN("院级管理员");
        final String desc;

        AdminRole(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public static AdminRole of(String value) {
            for (AdminRole role : values()) {
                if (role.desc.equals(value)) {
                    return role;
                }
            }
            return null;
        }
    }

    public Admin(){}

    public Admin(Integer id,String username,String password,String role,Integer college_id){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = AdminRole.of(role);
        CollegeDao cd = new CollegeDao();
        this.college = cd.getById(college_id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
