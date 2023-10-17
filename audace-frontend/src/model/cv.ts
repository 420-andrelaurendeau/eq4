import { Student } from "./user";

export interface CV {
  id?: number;
  fileName: string;
  content: string;
  student: Student;
  cvStatus: CVStatus;
}

export enum CVStatus {
    PENDING = "PENDING",
    ACCEPTED = "ACCEPTED",
    REFUSED = "REFUSED"
}