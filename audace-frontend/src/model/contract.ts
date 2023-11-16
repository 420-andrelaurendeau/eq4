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
}

export interface Supervisor{
  firstName: string;
  lastName: string;
  position: string;
  email: string;
  phone:string;
  extension: string;
}

export interface Signature {
  id: number;
  signatoryId: number;
  signatureDate: Date;
  contract: Contract;
}
