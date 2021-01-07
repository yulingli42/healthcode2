package com.healthcode.dto;

/**
 * 正在提交的每日一报信息
 *
 * @author zhenghong
 */
public class CurrentDailyCard {
    private boolean haveBeenToKeyEpidemicAreas;
    private boolean haveBeenAbroad;
    private boolean isTheExposed;
    private boolean isSuspectedCase;
    private String[] currentSymptoms;

    public CurrentDailyCard(){}
    public CurrentDailyCard(boolean haveBeenToKeyEpidemicAreas, boolean haveBeenAbroad, boolean isTheExposed, boolean isSuspectedCase, String[] currentSymptoms){
        this.haveBeenToKeyEpidemicAreas = haveBeenToKeyEpidemicAreas;
        this.haveBeenAbroad = haveBeenAbroad;
        this.isTheExposed = isTheExposed;
        this.isSuspectedCase = isSuspectedCase;
        this.currentSymptoms = currentSymptoms;
    }

    public boolean isHaveBeenToKeyEpidemicAreas() {
        return haveBeenToKeyEpidemicAreas;
    }

    public void setHaveBeenToKeyEpidemicAreas(boolean haveBeenToKeyEpidemicAreas) {
        this.haveBeenToKeyEpidemicAreas = haveBeenToKeyEpidemicAreas;
    }

    public boolean isHaveBeenAbroad() {
        return haveBeenAbroad;
    }

    public void setHaveBeenAbroad(boolean haveBeenAbroad) {
        this.haveBeenAbroad = haveBeenAbroad;
    }

    public boolean isTheExposed() {
        return isTheExposed;
    }

    public void setTheExposed(boolean theExposed) {
        isTheExposed = theExposed;
    }

    public boolean isSuspectedCase() {
        return isSuspectedCase;
    }

    public void setSuspectedCase(boolean suspectedCase) {
        isSuspectedCase = suspectedCase;
    }

    public String[] getCurrentSymptoms() {
        return currentSymptoms;
    }

    public void setCurrentSymptoms(String[] currentSymptoms) {
        this.currentSymptoms = currentSymptoms;
    }
}
