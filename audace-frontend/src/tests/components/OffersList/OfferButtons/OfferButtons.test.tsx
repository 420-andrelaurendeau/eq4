import { render, screen } from "@testing-library/react";
import OfferButtons from "../../../../components/OffersList/OfferRow/OfferButtons";
import { Offer, OfferStatus } from "../../../../model/offer";
import { UserType } from "../../../../model/user";
import "@testing-library/jest-dom/extend-expect";

const offer: Offer = {
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
  employer: {
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
  },
  offerStatus: OfferStatus.PENDING,
};

it("should render employer buttons when employer user type", () => {
  jest
    .spyOn(require("../../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Employer);

  render(<OfferButtons disabled={false} offer={offer} />);

  const editButton = screen.getByText(/offersList.editButton/i);
  expect(editButton).toBeInTheDocument();
});

it("should render student buttons when student user type", () => {
  jest
    .spyOn(require("../../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Student);

  render(<OfferButtons disabled={false} offer={offer} />);

  const applyButton = screen.getByText(/offersList.applyButton/i);
  expect(applyButton).toBeInTheDocument();
});

it("should render manager buttons when manager user type", () => {
  jest
    .spyOn(require("../../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Manager);

  render(<OfferButtons disabled={false} offer={offer} />);

  const acceptButton = screen.getByText(/offersList.acceptButton/i);
  expect(acceptButton).toBeInTheDocument();
});
