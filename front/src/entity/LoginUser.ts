export interface LoginUser {
    login: boolean
    type: "teacher" | "schoolAdmin" | "systemAdmin" | "collegeAdmin" | "student",
    name: string
}