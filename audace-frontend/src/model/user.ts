import { Department } from "./department";
import { Offer } from "./offer";

export interface User {
    id?: number;
    email: string;
    password: string;
}

export interface Student extends User{
    studentNumber: string;
    department: Department;
}

export interface Employer extends User{
    organisation: string;
    position: string;
    extension: string;
    offers: Offer[];
}