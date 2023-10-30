import Application from "./application";
import { Employer } from "./user";

export interface Contract {
  id?: number;
  officeName: string;
  startHour: number;
  endHour: number;
  totalHoursPerWeek: number;
  salary: number;
  internTasksAndResponsibilities: string;
  employer: Employer;
  application: Application;
}
