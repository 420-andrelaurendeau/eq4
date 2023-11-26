import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import EmployerButtons from "../../../components/ApplicationsList/ApplicationRow/ApplicationButtons/EmployerButtons";
import { application } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";
import Application, { ApplicationStatus } from "../../../model/application";

it("should display values", () => {
  render(<EmployerButtons application={application} />);

  expect(
    screen.getByText(/employerApplicationsList.acceptButton/i)
  ).toBeInTheDocument();
  expect(
    screen.getByText(/employerApplicationsList.refuseButton/i)
  ).toBeInTheDocument();
});

it("should call updateApplicationsState on accept button click", async () => {
  const updateApplicationsState = jest.fn();
  render(
    <EmployerButtons
      application={application}
      updateApplicationsState={updateApplicationsState}
    />
  );

  const acceptButton = screen.getByText(
    /employerApplicationsList.acceptButton/i
  );

  fireEvent.click(acceptButton);

  await waitFor(() => expect(updateApplicationsState).toHaveBeenCalledTimes(1));
  expect(updateApplicationsState).toHaveBeenCalledWith(application, "ACCEPTED");
});

it("should call updateApplicationsState on refuse button click", async () => {
  const updateApplicationsState = jest.fn();
  render(
    <EmployerButtons
      application={application}
      updateApplicationsState={updateApplicationsState}
    />
  );

  const refuseButton = screen.getByText(
    /employerApplicationsList.refuseButton/i
  );

  fireEvent.click(refuseButton);

  await waitFor(() => expect(updateApplicationsState).toHaveBeenCalledTimes(1));
  expect(updateApplicationsState).toHaveBeenCalledWith(application, "REFUSED");
});

it("should not display buttons if application status is not PENDING", () => {
  const newApplication: Application = {
    ...application,
    applicationStatus: ApplicationStatus.ACCEPTED,
  };
  render(<EmployerButtons application={newApplication} />);

  expect(
    screen.queryByText(/employerApplicationsList.acceptButton/i)
  ).not.toBeInTheDocument();
  expect(
    screen.queryByText(/employerApplicationsList.refuseButton/i)
  ).not.toBeInTheDocument();
});
