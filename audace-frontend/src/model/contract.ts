import Application from "./application";
import { Employer } from "./user";

export interface Contract {
  id?: number;
  startHour: string;
  endHour: string;
  totalHoursPerWeek: number;
  salary: number;
  supervisor: Employer;
  application: Application;
}
