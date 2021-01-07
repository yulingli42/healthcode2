package com.healthcode.domain;

import java.time.LocalDateTime;

/**
 * @author qianlei
 */
public class StudentDailyCard {
    private Integer id;
    private Student student;
    private HealthCodeType result;
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
