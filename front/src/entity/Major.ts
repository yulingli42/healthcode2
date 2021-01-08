import {College} from "./College";

export interface Major {
    id: number;
    name: string;
    college: College;
}