import { Department } from "./department";

export interface Offer {
    id?: number;
    title: string;
    description: string;
    internshipStartDate: Date;
    internshipEndDate: Date;
    offerEndDate: Date;
    department: Department;
    availablePlaces: number;
    employerId: number;
    status: OfferStatus;
}

export enum OfferStatus {
    PENDING = "PENDING",
    ACCEPTED = "ACCEPTED",
    REFUSED = "REFUSED"
}