import { Department } from "./department";

export interface Offer {
    id?: number;
    title: string;
    description: string;
    internshipStartDate: string;
    internshipEndDate: string;
    offerEndDate: string;
    availablePlaces: number;
    status: any;
    department: Department;
    employerId: number;
}