import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import EmployerButtons from "../../../../components/OffersList/OfferRow/OfferButtons/EmployerButtons";
import { Offer, OfferStatus } from "../../../../model/offer";
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

it("should display default values", () => {
  render(<EmployerButtons disabled={false} offer={offer} />);

  const editButton = screen.getByText(/offersList.editButton/i);
  expect(editButton).toBeInTheDocument();

  const deleteButton = screen.getByText(/offersList.deleteButton/i);
  expect(deleteButton).toBeInTheDocument();
});

it("should display seeApplications button", () => {
  render(
    <EmployerButtons
      disabled={false}
      offer={offer}
      seeApplications={() => {}}
    />
  );

  const seeApplicationsButton = screen.getByText(
    /employerOffersList.applicationButton/i
  );
  expect(seeApplicationsButton).toBeInTheDocument();
});

it("should disable buttons when disabled is true", () => {
  render(<EmployerButtons disabled={true} offer={offer} />);

  const editButton = screen.getByText(/offersList.editButton/i);
  expect(editButton).toBeDisabled();

  const deleteButton = screen.getByText(/offersList.deleteButton/i);
  expect(deleteButton).toBeDisabled();
});

it("should call seeApplications when seeApplications button is clicked", () => {
  const seeApplications = jest.fn();
  render(
    <EmployerButtons
      disabled={false}
      offer={offer}
      seeApplications={seeApplications}
    />
  );

  const seeApplicationsButton = screen.getByText(
    /employerOffersList.applicationButton/i
  );
  expect(seeApplicationsButton).toBeInTheDocument();

  fireEvent.click(seeApplicationsButton);

  expect(seeApplications).toHaveBeenCalledTimes(1);
});

it("should call useNavigate when edit button is clicked", () => {
  const navigate = jest.fn();
  jest
    .spyOn(require("react-router-dom"), "useNavigate")
    .mockImplementation(() => navigate);

  render(<EmployerButtons disabled={false} offer={offer} />);

  const editButton = screen.getByText(/offersList.editButton/i);
  expect(editButton).toBeInTheDocument();

  fireEvent.click(editButton);

  expect(navigate).toHaveBeenCalledWith("/employer/offers/1");
});

it("should call employerDeleteOffer when delete button is clicked", async () => {
  const employerDeleteOffer = jest.fn(() => Promise.resolve({ status: 200 }));
  jest
    .spyOn(require("../../../../services/offerService"), "employerDeleteOffer")
    .mockImplementation(employerDeleteOffer);

  jest
    .spyOn(require("../../../../services/authService"), "getUserId")
    .mockImplementation(() => "1");

  jest
    .spyOn(
      require("../../../../contextsholders/providers/SessionContextHolder"),
      "useSessionContext"
    )
    .mockImplementation(() => ({
      chosenSession: {
        id: 1,
        name: "name",
        startDate: new Date(),
        endDate: new Date(),
      },
      setChosenSession: () => {},
    }));

  render(<EmployerButtons disabled={false} offer={offer} />);

  const deleteButton = screen.getByText(/offersList.deleteButton/i);
  expect(deleteButton).toBeInTheDocument();

  fireEvent.click(deleteButton);

  await waitFor(() => expect(employerDeleteOffer).toHaveBeenCalledTimes(1));
});

it("should show error message when delete is unsuccessful", async () => {
  const employerDeleteOffer = jest.fn(() => Promise.resolve({ status: 400 }));
  jest
    .spyOn(require("../../../../services/offerService"), "employerDeleteOffer")
    .mockImplementation(employerDeleteOffer);

  jest
    .spyOn(require("../../../../services/authService"), "getUserId")
    .mockImplementation(() => "1");

  jest
    .spyOn(
      require("../../../../contextsholders/providers/SessionContextHolder"),
      "useSessionContext"
    )
    .mockImplementation(() => ({
      chosenSession: {
        id: 1,
        name: "name",
        startDate: new Date(),
        endDate: new Date(),
      },
      setChosenSession: () => {},
    }));

  render(<EmployerButtons disabled={false} offer={offer} />);

  const deleteButton = screen.getByText(/offersList.deleteButton/i);
  expect(deleteButton).toBeInTheDocument();

  fireEvent.click(deleteButton);

  await waitFor(() => expect(employerDeleteOffer).toHaveBeenCalledTimes(1));
});
