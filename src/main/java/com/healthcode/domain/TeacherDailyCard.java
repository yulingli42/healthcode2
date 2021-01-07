package com.healthcode.domain;

import java.time.LocalDateTime;

/**
 * @author qianlei
 */
public class TeacherDailyCard {
    private Integer id;
    private Teacher teacher;
    private HealthCodeType result;
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public HealthCodeType getResult() {
        return result;
    }

    public void setResult(HealthCodeType result) {
        this.result = result;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
