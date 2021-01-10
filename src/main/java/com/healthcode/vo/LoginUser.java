package com.healthcode.vo;

import com.healthcode.common.HealthCodeException;
import com.healthcode.domain.Admin;
import com.healthcode.domain.Student;
import com.healthcode.domain.Teacher;

/**
 * @author qianlei
 */
public class LoginUser {
    private final Boolean login;
    private final String type;
    private final Object user;

    public LoginUser(Object user) {
        login = user != null;
        if (user == null) {
            type = "";
            this.user = null;
            return;
        } else if (user instanceof Admin) {
            type = "admin";
        } else if (user instanceof Teacher) {
            type = "teacher";
        } else if (user instanceof Student) {
            type = "student";
        } else {
            throw new HealthCodeException("未知类型");
        }
        this.user = user;
    }

    public Boolean getLogin() {
        return login;
    }

    public Object getUser() {
        return user;
    }

    public String getType() {
        return type;
    }
}
