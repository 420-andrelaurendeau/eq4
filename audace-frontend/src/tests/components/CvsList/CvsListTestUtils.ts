import { CV, CVStatus } from "../../../model/cv";

export const cv: CV = {
  id: 1,
  fileName: "The best CV",
  content: "This is the best CV ever",
  cvStatus: CVStatus.PENDING,
  student: {
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
  },
};
