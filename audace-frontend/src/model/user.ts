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
  Manager,
}

export interface StudentsWithStatus {
  students: Student[];
  status: string;
}

export enum InternshipStatus {
  studentsWithIntership = "INTERN",
  studentsWithPendingResponse = "PENDING",
  studentsWithRefusedResponse = "REFUSED",
  studentsWithAcceptedResponse = "ACCEPTED",
  studentsWithNoApplications = "NO_APPLICATION",
}

export interface StudentsByInternshipFoundStatus {
  [key: string]: StudentsWithStatus;
}

export const mapStudentsWithStatus = (
  statusKey: string,
  response: any
): StudentsWithStatus => ({
  students: response[statusKey],
  status:
    InternshipStatus[statusKey as keyof typeof InternshipStatus] || statusKey,
});
