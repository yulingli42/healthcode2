import {Teacher} from "./Teacher";
import {Student} from "./Student";
import {Admin} from "./Admin";

export interface LoginUser {
    login: boolean;
    type: "admin" | "teacher" | "student";
    user: Teacher | Student | Admin;
}