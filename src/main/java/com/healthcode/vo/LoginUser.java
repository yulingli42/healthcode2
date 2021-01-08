package com.healthcode.vo;

/**
 * @author qianlei
 */
public class LoginUser {
    private Boolean login;
    private String type;
    private String name;

    public LoginUser() {
        login = false;
    }

    public LoginUser(String type, String name) {
        login = true;
        this.type = type;
        this.name = name;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
