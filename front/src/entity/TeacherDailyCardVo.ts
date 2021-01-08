import {HealthCodeType} from "./HealthCodeType";

export interface TeacherDailyCardVo {
    teacherId: number,
    name: string,
    className: string,
    type: HealthCodeType
}