import Application, { ApplicationStatus } from "../../../model/application";
import { CV, CVStatus } from "../../../model/cv";
import { Offer, OfferStatus } from "../../../model/offer";
import { Employer, Manager, Student } from "../../../model/user";

export const employer: Employer = {
  id: 1,
  firstName: "firstName",
  lastName: "lastName",
  email: "email",
  phone: "phone",
  address: "address",
  password: "password",
  type: "employer",
  organisation: "organisation",
  position: "position",
  extension: "extension",
};

export const offer: Offer = {
  id: 1,
  title: "title",
  description: "description",
  internshipStartDate: new Date(),
  internshipEndDate: new Date(),
  offerEndDate: new Date(),
  availablePlaces: 1,
  department: {
    id: 1,
    code: "code",
    name: "name",
  },
  employer: employer,
  offerStatus: OfferStatus.PENDING,
};

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
  type: "student",
};

export const cv: CV = {
  id: 1,
  fileName: "The best CV",
  content: "This is the best CV ever",
  cvStatus: CVStatus.PENDING,
  student: student,
};

export const application: Application = {
  id: 1,
  offer: offer,
  cv: cv,
  applicationStatus: ApplicationStatus.PENDING,
};

export const manager: Manager = {
  id: 1,
  firstName: "firstName",
  lastName: "lastName",
  email: "email",
  phone: "phone",
  address: "address",
  password: "password",
  type: "manager",
  department: {
    id: 1,
    code: "code",
    name: "name",
  },
};
