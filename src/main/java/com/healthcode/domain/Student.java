package com.healthcode.domain;

/**
 * @author qianlei
 */
public class Student {
    private String id;
    private String name;
    private String password;
    private String idCard;
    private Clazz clazz;

    public Student(){}

    public Student(String id, String name, String password, String idCard, Clazz clazz){
        this.id = id;
        this.name = name;
        this.password = password;
        this.idCard = idCard;
        this.clazz = clazz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }
}
