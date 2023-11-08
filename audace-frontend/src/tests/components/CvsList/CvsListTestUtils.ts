import { CV, CVStatus } from "../../../model/cv";
import { Student } from "../../../model/user";

export const student: Student = {
  id: 1,
  firstName: "John",
  lastName: "Doe",
  email: "",
  phone: "",
  address: "",
  password: "",
  studentNumber: "123456789",
  department: {
    id: 1,
    name: "Computer Science",
    code: "CS",
  },
};

export const cv: CV = {
  id: 1,
  fileName: "The best CV",
  content: "This is the best CV ever",
  cvStatus: CVStatus.PENDING,
  student: student,
};
