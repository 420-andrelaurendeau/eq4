import { render, screen } from "@testing-library/react";
import OfferModal from "../../../components/OffersList/OfferRow/OfferModal";
import "@testing-library/jest-dom/extend-expect";
import { employer, offer } from "../testUtils/testUtils";

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
