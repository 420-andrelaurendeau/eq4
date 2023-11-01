import { render, screen } from "@testing-library/react";
import { Offer, OfferStatus } from "../../../model/offer";
import OffersList from "../../../components/OffersList";
import { UserType } from "../../../model/user";

const offers: Offer[] = [
  {
    id: 1,
    title: "test",
    description: "test",
    internshipStartDate: new Date("2021-01-01"),
    internshipEndDate: new Date(),
    offerStatus: OfferStatus.PENDING,
    employer: {
      id: 1,
      organisation: "test",
      extension: "test",
      position: "test",
      email: "test",
      phone: "test",
      address: "test",
      password: "test",
      type: "employer",
    },
    department: {
      id: 1,
      code: "test",
      name: "test",
    },
    offerEndDate: new Date(),
    availablePlaces: 1,
  },
];

it("should render a table", () => {
  render(<OffersList offers={offers} error="" userType={UserType.Student} />);

  const table = screen.getByRole("table");
  expect(table).not.toBeUndefined();
});
