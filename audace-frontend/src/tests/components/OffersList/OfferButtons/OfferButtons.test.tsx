import { render, screen } from "@testing-library/react";
import OfferButtons from "../../../../components/OffersList/OfferRow/OfferButtons";
import { UserType } from "../../../../model/user";
import "@testing-library/jest-dom/extend-expect";
import { offer } from "../../testUtils/testUtils";

it("should render employer buttons when employer user type", () => {
  jest
    .spyOn(require("../../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Employer);

  render(<OfferButtons offer={offer} />);

  const editButton = screen.getByText(/offersList.editButton/i);
  expect(editButton).toBeInTheDocument();
});

it("should render student buttons when student user type", () => {
  jest
    .spyOn(require("../../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Student);

  render(<OfferButtons offer={offer} />);

  const applyButton = screen.getByText(/offersList.applyButton/i);
  expect(applyButton).toBeInTheDocument();
});

it("should render manager buttons when manager user type", () => {
  jest
    .spyOn(require("../../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Manager);

  render(<OfferButtons offer={offer} />);

  const acceptButton = screen.getByText(/offersList.acceptButton/i);
  expect(acceptButton).toBeInTheDocument();
});
