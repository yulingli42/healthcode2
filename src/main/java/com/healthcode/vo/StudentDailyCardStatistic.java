package com.healthcode.vo;

import java.util.List;

/**
 * @author qianlei
 */
public class StudentDailyCardStatistic {
    private Integer totalStudentCount;
    private Integer greenCodeStudentCount;
    private Integer yellowCodeStudentCount;
    private Integer redCodeStudentCount;
    private List<StudentDailyCardVo> dailyCardList;

    public Integer getTotalStudentCount() {
        return totalStudentCount;
    }

    public void setTotalStudentCount(Integer totalStudentCount) {
        this.totalStudentCount = totalStudentCount;
    }

    public Integer getGreenCodeStudentCount() {
        return greenCodeStudentCount;
    }

    public void setGreenCodeStudentCount(Integer greenCodeStudentCount) {
        this.greenCodeStudentCount = greenCodeStudentCount;
    }

    public Integer getYellowCodeStudentCount() {
        return yellowCodeStudentCount;
    }

    public void setYellowCodeStudentCount(Integer yellowCodeStudentCount) {
        this.yellowCodeStudentCount = yellowCodeStudentCount;
    }

    public Integer getRedCodeStudentCount() {
        return redCodeStudentCount;
    }

    public void setRedCodeStudentCount(Integer redCodeStudentCount) {
        this.redCodeStudentCount = redCodeStudentCount;
    }

    public List<StudentDailyCardVo> getDailyCardList() {
        return dailyCardList;
    }

    public void setDailyCardList(List<StudentDailyCardVo> dailyCardList) {
        this.dailyCardList = dailyCardList;
    }
}
