import { Department } from "./department";
import { Employer } from "./user";

export interface Offer {
  id?: number;
  title: string;
  description: string;
  internshipStartDate: Date;
  internshipEndDate: Date;
  offerEndDate: Date;
  availablePlaces: number;
  department: Department;
  employer: Employer;
  status: OfferStatus;
}

export enum OfferStatus {
  PENDING = "PENDING",
  ACCEPTED = "ACCEPTED",
  REFUSED = "REFUSED",
}
