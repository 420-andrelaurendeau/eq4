import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import EmployerButtons from "../../../../components/OffersList/OfferRow/OfferButtons/EmployerButtons";
import "@testing-library/jest-dom/extend-expect";
import { offer } from "../../testUtils/testUtils";

/*it("should display default values", () => {
  render(<EmployerButtons offer={offer} />);

  const editButton = screen.getByText(/offersList.editButton/i);
  expect(editButton).toBeInTheDocument();

  const deleteButton = screen.getByText(/offersList.deleteButton/i);
  expect(deleteButton).toBeInTheDocument();
});*/

/*it("should display seeApplications button", () => {
  render(<EmployerButtons offer={offer} />);

  const seeApplicationsButton = screen.getByText(
    /employerOffersList.applicationButton/i
  );
  expect(seeApplicationsButton).toBeInTheDocument();
});*/

/*it("should call useNavigate when edit button is clicked", () => {
  const navigate = jest.fn();
  jest
    .spyOn(require("react-router-dom"), "useNavigate")
    .mockImplementation(() => navigate);

  render(<EmployerButtons offer={offer} />);

  const editButton = screen.getByText(/offersList.editButton/i);
  expect(editButton).toBeInTheDocument();

  fireEvent.click(editButton);

  expect(navigate).toHaveBeenCalledWith("/employer/offers/1");
});*/

/*it("should call employerDeleteOffer when delete button is clicked", async () => {
  jest.spyOn(console, "log").mockImplementation(() => {});

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

  render(<EmployerButtons offer={offer} />);

  const deleteButton = screen.getByText(/offersList.deleteButton/i);
  expect(deleteButton).toBeInTheDocument();

  fireEvent.click(deleteButton);

  await waitFor(() => expect(employerDeleteOffer).toHaveBeenCalledTimes(1));
});*/

/*it("should show error message when delete is unsuccessful", async () => {
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

  jest.spyOn(console, "error").mockImplementation(() => {});

  render(<EmployerButtons offer={offer} />);

  const deleteButton = screen.getByText(/offersList.deleteButton/i);
  expect(deleteButton).toBeInTheDocument();

  fireEvent.click(deleteButton);

  await waitFor(() => expect(employerDeleteOffer).toHaveBeenCalledTimes(1));
});*/
