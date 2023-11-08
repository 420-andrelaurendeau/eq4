import Application from "./application";

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
