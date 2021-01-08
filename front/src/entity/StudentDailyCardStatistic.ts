import {StudentDailyCardVo} from "./StudentDailyCardVo";

export interface StudentDailyCardStatistic {
    totalStudentCount: number;
    greenCodeStudentCount: number;
    yellowCodeStudentCount: number;
    redCodeStudentCount: number;
    dailyCardList: StudentDailyCardVo[];
}