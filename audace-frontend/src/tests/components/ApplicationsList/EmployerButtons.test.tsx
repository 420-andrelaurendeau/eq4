import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import EmployerButtons from "../../../components/ApplicationsList/ApplicationRow/ApplicationButtons/EmployerButtons";
import { application, offer } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";

it("should display values", () => {
  render(<EmployerButtons application={application} offer={offer} />);

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
      offer={offer}
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
      offer={offer}
    />
  );

  const refuseButton = screen.getByText(
    /employerApplicationsList.refuseButton/i
  );

  fireEvent.click(refuseButton);

  await waitFor(() => expect(updateApplicationsState).toHaveBeenCalledTimes(1));
  expect(updateApplicationsState).toHaveBeenCalledWith(application, "REFUSED");
});
