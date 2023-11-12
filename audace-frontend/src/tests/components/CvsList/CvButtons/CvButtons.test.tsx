import { render, screen } from "@testing-library/react";
import CvButtons from "../../../../components/CVsList/CvRow/CvButtons";
import { UserType } from "../../../../model/user";
import "@testing-library/jest-dom/extend-expect";
import { cv } from "../../testUtils/testUtils";

it("should display ManagerButtons when user is manager", () => {
  jest
    .spyOn(require("../../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Manager);

  render(<CvButtons cv={cv} />);

  const acceptButton = screen.getByText(/managerCvsList.acceptButton/i);
  expect(acceptButton).toBeInTheDocument();

  const refuseButton = screen.getByText(/managerCvsList.refuseButton/i);
  expect(refuseButton).toBeInTheDocument();
});
