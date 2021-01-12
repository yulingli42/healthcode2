import {College} from "./College";

export interface Admin {
    id: number;
    username: string;
    role: AdminRole;
    college: College;
}

export enum AdminRole {
    SYSTEM_ADMIN = "SYSTEM_ADMIN",
    /**
     * 校级管理员
     */
    SCHOOL_ADMIN = "SCHOOL_ADMIN",
    /**
     * 院级管理员
     */
    COLLEGE_ADMIN = "COLLEGE_ADMIN"
}

