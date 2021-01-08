package com.healthcode.vo;

import java.util.List;

/**
 * @author qianlei
 */
public class TeacherDailyCardStatistic {
    private Integer totalTeacherCount;
    private Integer greenCodeTeacherCount;
    private Integer yellowCodeTeacherCount;
    private Integer redCodeTeacherCount;
    private List<TeacherDailyCardVo> dailyCardList;

    public TeacherDailyCardStatistic() {
    }

    public TeacherDailyCardStatistic(Integer totalTeacherCount, Integer greenCodeTeacherCount, Integer yellowCodeTeacherCount, Integer redCodeTeacherCount, List<TeacherDailyCardVo> dailyCardList) {
        this.totalTeacherCount = totalTeacherCount;
        this.greenCodeTeacherCount = greenCodeTeacherCount;
        this.yellowCodeTeacherCount = yellowCodeTeacherCount;
        this.redCodeTeacherCount = redCodeTeacherCount;
        this.dailyCardList = dailyCardList;
    }

    public Integer getTotalTeacherCount() {
        return totalTeacherCount;
    }

    public void setTotalTeacherCount(Integer totalTeacherCount) {
        this.totalTeacherCount = totalTeacherCount;
    }

    public Integer getGreenCodeTeacherCount() {
        return greenCodeTeacherCount;
    }

    public void setGreenCodeTeacherCount(Integer greenCodeTeacherCount) {
        this.greenCodeTeacherCount = greenCodeTeacherCount;
    }

    public Integer getYellowCodeTeacherCount() {
        return yellowCodeTeacherCount;
    }

    public void setYellowCodeTeacherCount(Integer yellowCodeTeacherCount) {
        this.yellowCodeTeacherCount = yellowCodeTeacherCount;
    }

    public Integer getRedCodeTeacherCount() {
        return redCodeTeacherCount;
    }

    public void setRedCodeTeacherCount(Integer redCodeTeacherCount) {
        this.redCodeTeacherCount = redCodeTeacherCount;
    }

    public List<TeacherDailyCardVo> getDailyCardList() {
        return dailyCardList;
    }

    public void setDailyCardList(List<TeacherDailyCardVo> dailyCardList) {
        this.dailyCardList = dailyCardList;
    }
}
