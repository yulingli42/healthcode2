package com.healthcode.domain;

/**
 * 班级实体类
 *
 * @author qianlei
 */
public class Clazz {
    private Integer id;
    private String name;
    private Major major;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
