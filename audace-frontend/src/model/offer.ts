import { Department } from "./department";
import { Employer } from "./user";

export interface Offer {
    id?: number;
    description: string;
    internshipStartDate: Date;
    internshipEndDate: Date;
    offerEndDate: Date;
    department: Department;
    employer: Employer;
}