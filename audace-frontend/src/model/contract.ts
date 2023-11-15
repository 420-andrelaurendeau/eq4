import Application from "./application";
import { User } from "./user";

export interface Contract {
  id?: number;
  startHour: string;
  endHour: string;
  totalHoursPerWeek: number;
  salary: number;
  supervisor: Supervisor;
  application: Application;
  studentSignature?: Signature;
  employerSignature?: Signature;
  managerSignature?: Signature;
}

export interface Supervisor{
  firstName: string;
  lastName: string;
  position: string;
  email: string;
  phone:string;
  extension: string;
}

export interface Signature{
  signatory: User;
  signatureDate: Date;
}
