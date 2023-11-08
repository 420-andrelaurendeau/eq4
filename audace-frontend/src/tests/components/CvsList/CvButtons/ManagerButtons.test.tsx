import { render, screen, waitFor } from "@testing-library/react";
import ManagerButtons from "../../../../components/CVsList/CvRow/CvButtons/ManagerButtons";
import { cv } from "../CvsListTestUtils";
import "@testing-library/jest-dom/extend-expect";
import { CVStatus } from "../../../../model/cv";

it("should display buttons properly", () => {
  render(<ManagerButtons cv={cv} />);

  const acceptButton = screen.getByText(/managerCvsList.acceptButton/i);
  expect(acceptButton).toBeInTheDocument();

  const refuseButton = screen.getByText(/managerCvsList.refuseButton/i);
  expect(refuseButton).toBeInTheDocument();
});

it("should display status properly", () => {
  render(<ManagerButtons cv={{ ...cv, cvStatus: CVStatus.ACCEPTED }} />);

  const acceptedStatus = screen.getByText(/managerCvsList.accepted/i);
  expect(acceptedStatus).toBeInTheDocument();
});

it("should call updateCvsState when accept button is clicked", async () => {
  const updateCvsStateMock = jest.fn();
  render(
    <ManagerButtons
      cv={cv}
      updateCvsState={updateCvsStateMock}
      disabled={false}
    />
  );

  const acceptButton = screen.getByText(/managerCvsList.acceptButton/i);
  acceptButton.click();

  await waitFor(() => expect(updateCvsStateMock).toBeCalledTimes(1));
});

it("should call updateCvsState when refuse button is clicked", async () => {
  const updateCvsStateMock = jest.fn();
  render(
    <ManagerButtons
      cv={cv}
      updateCvsState={updateCvsStateMock}
      disabled={false}
    />
  );

  const refuseButton = screen.getByText(/managerCvsList.refuseButton/i);
  refuseButton.click();

  await waitFor(() => expect(updateCvsStateMock).toBeCalledTimes(1));
});
