import {HealthCodeType} from "./HealthCodeType";

export interface TeacherDailyCardVo {
    teacherId: number,
    name: string,
    className: string,
    hadSubmitDailyCard: boolean,
    type: HealthCodeType
}