import {TeacherDailyCardVo} from "./TeacherDailyCardVo";

export interface TeacherDailyCardStatistic {
    totalTeacherCount: number,
    greenCodeTeacherCount: number;
    yellowCodeTeacherCount: number;
    redCodeTeacherCount: number;
    dailyCardList: TeacherDailyCardVo[];
}