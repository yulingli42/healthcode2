import {HealthCodeType} from "./HealthCodeType";

export interface StudentDailyCardVo {
    studentId: String;
    name: String;
    className: String;
    majorName: String;
    collegeName: String;
    type: HealthCodeType;
}