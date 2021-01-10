package com.healthcode.vo;

import com.healthcode.domain.HealthCodeType;

/**
 * @author qianlei
 */
public class TeacherDailyCardVo {
    private String teacherId;
    private String name;
    private String collegeName;
    private HealthCodeType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public HealthCodeType getType() {
        return type;
    }

    public void setType(HealthCodeType type) {
        this.type = type;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherDailyCardVo() {
    }
}
