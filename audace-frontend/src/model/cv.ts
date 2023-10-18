import { Student } from "./user";

export interface CV {
  id?: number;
  fileName: string;
  content: string;
  student: Student;
}
