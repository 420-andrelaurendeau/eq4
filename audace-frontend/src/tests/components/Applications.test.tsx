import "@testing-library/jest-dom/extend-expect";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import Applications from "../../components/Applications";
import { application, offer } from "./testUtils/testUtils";
import { UserType } from "../../model/user";
import { ApplicationStatus } from "../../model/application";

beforeEach(() => {
  application.applicationStatus = ApplicationStatus.PENDING;

  jest
    .spyOn(require("../../services/authService"), "getUserId")
    .mockReturnValue("1");
});

it("should display the application table", () => {
  render(<Applications offer={offer} applications={[application]} error="" />);

  expect(screen.getByText(/applicationsList.title/i)).toBeInTheDocument();
});

describe("button clicks", () => {
  beforeEach(() => {
    jest
      .spyOn(
        require("../../services/applicationService"),
        "employerAcceptApplication"
      )
      .mockResolvedValue({});

    jest
      .spyOn(
        require("../../services/applicationService"),
        "employerRefuseApplication"
      )
      .mockResolvedValue({});
  });

  it("should call updateApplicationsState when accept button is clicked", async () => {
    jest
      .spyOn(require("../../services/authService"), "getUserType")
      .mockImplementation(() => UserType.Employer);

    const updateApplicationsState = jest.fn();

    render(
      <Applications
        offer={offer}
        updateApplicationsState={updateApplicationsState}
        updateAvailablePlaces={jest.fn()}
        applications={[application]}
        error=""
      />
    );

    const acceptButton = await screen.findByText(
      /employerApplicationsList.acceptButton/i
    );

    fireEvent.click(acceptButton);

    await waitFor(() =>
      expect(updateApplicationsState).toHaveBeenCalledTimes(1)
    );
  });

  it("should call updateApplicationsState when refuse button is clicked", async () => {
    jest
      .spyOn(require("../../services/authService"), "getUserType")
      .mockImplementation(() => UserType.Employer);

    const updateApplicationsState = jest.fn();

    render(
      <Applications
        offer={offer}
        updateApplicationsState={updateApplicationsState}
        updateAvailablePlaces={jest.fn()}
        applications={[application]}
        error=""
      />
    );

    const refuseButton = await screen.findByText(
      /employerApplicationsList.refuseButton/i
    );

    fireEvent.click(refuseButton);

    await waitFor(() =>
      expect(updateApplicationsState).toHaveBeenCalledTimes(1)
    );
  });
});
