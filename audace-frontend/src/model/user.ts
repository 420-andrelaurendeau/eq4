import { Department } from "./department";

export interface User {
  id?: number;
  firstName?: string;
  lastName?: string;
  email: string;
  phone?: string;
  address?: string;
  password: string;
  type?: string;
}

export interface Student extends User {
  studentNumber: string;
  department?: Department;
}

export interface Employer extends User {
  organisation: string;
  position: string;
  extension: string;
}

export interface Manager extends User {
  department: Department;
}

export enum UserType {
  Student,
  Employer,
  Manager
}