package com.healthcode.vo;

import com.healthcode.domain.HealthCodeType;

/**
 * @author qianlei
 */
public class StudentDailyCardVo {
    private String studentId;
    private String name;
    private String className;
    private String majorName;
    private String collegeName;
    private Boolean hadSubmitDailyCard;
    private HealthCodeType type;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHadSubmitDailyCard() {
        return hadSubmitDailyCard;
    }

    public void setHadSubmitDailyCard(Boolean hadSubmitDailyCard) {
        this.hadSubmitDailyCard = hadSubmitDailyCard;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public HealthCodeType getType() {
        return type;
    }

    public void setType(HealthCodeType type) {
        this.type = type;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
