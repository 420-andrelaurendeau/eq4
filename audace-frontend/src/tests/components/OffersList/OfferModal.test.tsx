import { render, screen } from "@testing-library/react";
import OfferModal from "../../../components/OffersList/OfferRow/OfferModal";
import { Offer, OfferStatus } from "../../../model/offer";
import { Employer } from "../../../model/user";
import "@testing-library/jest-dom/extend-expect";

const employer: Employer = {
  id: 1,
  firstName: "firstName",
  lastName: "lastName",
  email: "email",
  phone: "phone",
  address: "address",
  password: "password",
  type: "employer",
  organisation: "organisation",
  position: "position",
  extension: "extension",
};

const offer: Offer = {
  id: 1,
  title: "title",
  description: "description",
  internshipStartDate: new Date(),
  internshipEndDate: new Date(),
  offerEndDate: new Date(),
  availablePlaces: 1,
  department: {
    id: 1,
    code: "code",
    name: "name",
  },
  employer: employer,
  offerStatus: OfferStatus.PENDING,
};

it("is visible when props is true", () => {
  render(
    <OfferModal
      show={true}
      offer={offer}
      handleClose={() => {}}
      disabled={false}
      employer={employer}
    />
  );

  const orgText = screen.getByText(/offer.modal.org/i);
  expect(orgText).toBeInTheDocument();

  const addressText = screen.getByText(/offer.modal.address/i);
  expect(addressText).toBeInTheDocument();

  const phoneText = screen.getByText(/offer.modal.phone/i);
  expect(phoneText).toBeInTheDocument();

  const internDateStartText = screen.getByText(/offer.modal.internDate.start/i);
  expect(internDateStartText).toBeInTheDocument();

  const internDateEndText = screen.getByText(/offer.modal.internDate.end/i);
  expect(internDateEndText).toBeInTheDocument();

  const offerEndText = screen.getByText(/offer.modal.offerEnd/i);
  expect(offerEndText).toBeInTheDocument();
});
