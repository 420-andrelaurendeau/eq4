import { Department } from "./department";
import {useTranslation} from "react-i18next";

const { t } = useTranslation();
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

export interface StudentsWithInternship {
  students: Student[];
  status: "INTERN";
}

export interface StudentsWithAcceptedResponse {
    students: Student[];
    status: "ACCEPTED";
}

export interface StudentsWithRefusedResponse {
    students: Student[];
    status: "REFUSED";
}

export interface StudentsWithNoApplications {
    students: Student[];
    status: "NO_APPLICATIONS";
}

export interface StudentsWithPendingResponse {
    students: Student[];
    status: "PENDING";
}

export interface StudentsByInternshipFoundStatus {
    studentsWithInternship: StudentsWithInternship;
    studentsWithAcceptedResponse: StudentsWithAcceptedResponse;
    studentsWithPendingResponse: StudentsWithPendingResponse;
    studentsWithRefusedResponse: StudentsWithRefusedResponse;
    studentsWithoutApplications: StudentsWithNoApplications;
}