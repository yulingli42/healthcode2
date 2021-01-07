package com.healthcode.domain;

/**
 * 班级实体类
 *
 * @author qianlei
 */
public class Clazz {
    private Integer id;
    private String name;
    private Profession profession;

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

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
