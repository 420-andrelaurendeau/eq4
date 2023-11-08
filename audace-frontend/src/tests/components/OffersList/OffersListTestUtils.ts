import { Offer, OfferStatus } from "../../../model/offer";
import { Employer } from "../../../model/user";

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
