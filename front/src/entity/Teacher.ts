import {College} from "./College";

export interface Teacher {
    id: number;
    name: string;
    idCard: string;
    college: College;
}