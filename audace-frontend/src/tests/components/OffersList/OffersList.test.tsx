import { render, screen } from "@testing-library/react";
import OffersList from "../../../components/OffersList";
import "@testing-library/jest-dom/extend-expect";
import { offer } from "../testUtils/testUtils";
import { UserType } from "../../../model/user";

jest.mock("../../../components/Applications", () => () => (
  <div>We are here</div>
));

jest
  .spyOn(require("../../../services/authService"), "getUserId")
  .mockReturnValue("1");

jest
  .spyOn(require("../../../services/authService"), "getUserType")
  .mockReturnValue(UserType.Student);

jest
  .spyOn(
    require("../../../contextsholders/providers/SessionContextHolder"),
    "useSessionContext"
  )
  .mockImplementation(() => ({
    chosenSession: {
      id: 1,
      startDate: new Date("2021-01-01"),
      endDate: new Date("2021-01-02"),
    },
    currentSession: {
      id: 1,
      startDate: new Date("2021-01-01"),
      endDate: new Date("2021-01-02"),
    },
  }));

it("should display basic table head values when offers not empty", () => {
  render(<OffersList offers={[offer]} error="" />);

  const title = screen.getByText(/offersList.title/i);
  expect(title).toBeInTheDocument();

  const internshipStartDate = screen.getByText(
    /offersList.internshipStartDate/i
  );
  expect(internshipStartDate).toBeInTheDocument();

  const internshipEndDate = screen.getByText(/offersList.internshipEndDate/i);
  expect(internshipEndDate).toBeInTheDocument();

  const availablePlaces = screen.getByText(/offersList.availablePlaces/i);
  expect(availablePlaces).toBeInTheDocument();
});

it("should display empty offers message when offers is empty", () => {
  render(<OffersList offers={[]} error="" />);

  const emptyOffers = screen.getByText(/offersList.noOffers/i);
  expect(emptyOffers).toBeInTheDocument();
});
