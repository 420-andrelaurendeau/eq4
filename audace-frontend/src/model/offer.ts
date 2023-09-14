import { Department } from "./department";

export interface Offer {
    id?: number;
    title: string;
    description: string;
    internshipStartDate: Date;
    internshipEndDate: Date;
    offerEndDate: Date;
    department: Department;
    employerId: number;
}