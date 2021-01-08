package com.healthcode.vo;

import com.healthcode.domain.HealthCodeType;

/**
 * @author qianlei
 */
public class TeacherDailyCardVo {
    private Integer teacherId;
    private String name;
    private String className;
    private HealthCodeType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public HealthCodeType getType() {
        return type;
    }

    public void setType(HealthCodeType type) {
        this.type = type;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherDailyCardVo() {
    }
}
