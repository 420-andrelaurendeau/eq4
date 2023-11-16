import Application from "./application";
import { Employer, Manager, Student, User } from "./user";

export interface Contract {
  id?: number;
  startHour: string;
  endHour: string;
  totalHoursPerWeek: number;
  salary: number;
  supervisor: Supervisor;
  application: Application;
  studentSignature?: Signature<Student>;
  employerSignature?: Signature<Employer>;
  managerSignature?: Signature<Manager>;
}

export interface Supervisor{
  firstName: string;
  lastName: string;
  position: string;
  email: string;
  phone:string;
  extension: string;
}

export interface Signature<T extends User> {
  signatory: T;
  signatureDate: Date;
}
