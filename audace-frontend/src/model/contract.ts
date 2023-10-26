import { Student } from "./user";

export interface Contract {
	id?: number;
	officeName: string;
	startHour: string;
	endHour: string;
	totalHoursPerWeek: number;
	salary: number;
	internTasksAndResponsibilities: string;
}
