import "@testing-library/jest-dom/extend-expect";
import { fireEvent, render, screen } from "@testing-library/react";
import Applications from "../../components/Applications";
import { application, offer } from "./testUtils/testUtils";
import { UserType } from "../../model/user";
import { ApplicationStatus } from "../../model/application";

beforeEach(() => {
  application.applicationStatus = ApplicationStatus.PENDING;

  jest
    .spyOn(require("../../services/authService"), "getUserId")
    .mockImplementation(() => 1);

  jest
    .spyOn(
      require("../../services/applicationService"),
      "getAllApplicationsByEmployerIdAndOfferId"
    )
    .mockImplementation(() => Promise.resolve({ data: [application] }));
});

/*it("should display the application table", () => {
  render(<Applications offer={offer} />);

  expect(screen.getByText(/applicationsList.title/i)).toBeInTheDocument();
});*/

/*it("should call updateApplicationsState when accept button is clicked", async () => {
  jest
    .spyOn(require("../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Employer);

  render(<Applications offer={offer} updateAvailablePlaces={jest.fn()} />);

  const acceptButton = await screen.findByText(
    /employerApplicationsList.acceptButton/i
  );

  fireEvent.click(acceptButton);
});*/

/*it("should call updateApplicationsState when refuse button is clicked", async () => {
  jest
    .spyOn(require("../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Employer);

  render(<Applications offer={offer} updateAvailablePlaces={jest.fn()} />);

  const refuseButton = await screen.findByText(
    /employerApplicationsList.refuseButton/i
  );

  fireEvent.click(refuseButton);
});*/
