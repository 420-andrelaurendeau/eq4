import { Department } from "./department";
import { Offer } from "./offer";

export interface User {
    id?: number;
    firstName?: string;
    lastName?: string;
    email: string;
    phone?: string;
    address?: string;
    password: string;
}

export interface Student extends User{
    studentNumber: string;
    department?: Department;
}

export interface Employer extends User{
    organisation: string;
    position: string;
    extension: string;
    offers: Offer[];
}

export enum UserType {
    Student,
    Employer
}