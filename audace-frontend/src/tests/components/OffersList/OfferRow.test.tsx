import { fireEvent, render, screen } from "@testing-library/react";
import OfferRow from "../../../components/OffersList/OfferRow";
import "@testing-library/jest-dom/extend-expect";
import { Table } from "react-bootstrap";
import { offer } from "./OffersListTestUtils";

// Rendered in a table and tbody to avoid getting warnings

it("should display default values", () => {
  render(
    <Table>
      <tbody>
        <OfferRow offer={offer} />
      </tbody>
    </Table>
  );

  const viewMore = screen.getByText(/offersList.viewMore/i);
  expect(viewMore).toBeInTheDocument();
});

it("should open modal when view more is clicked", async () => {
  render(
    <Table>
      <tbody>
        <OfferRow offer={offer} />
      </tbody>
    </Table>
  );

  const viewMore = screen.getByText(/offersList.viewMore/i);
  expect(viewMore).toBeInTheDocument();

  fireEvent.click(viewMore);

  const orgText = await screen.findByText(/offer.modal.org/i);
  expect(orgText).toBeInTheDocument();

  const addressText = screen.getByText(/offer.modal.address/i);
  expect(addressText).toBeInTheDocument();

  const phoneText = screen.getByText(/offer.modal.phone/i);
  expect(phoneText).toBeInTheDocument();

  const internDateStartText = screen.getByText(/offer.modal.internDate.start/i);
  expect(internDateStartText).toBeInTheDocument();

  const internDateEndText = screen.getByText(/offer.modal.internDate.end/i);
  expect(internDateEndText).toBeInTheDocument();
});

it("should close modal when close is clicked", async () => {
  render(
    <Table>
      <tbody>
        <OfferRow offer={offer} />
      </tbody>
    </Table>
  );

  const viewMore = screen.getByText(/offersList.viewMore/i);
  expect(viewMore).toBeInTheDocument();

  fireEvent.click(viewMore);

  const closeButton = screen.getByLabelText(/close/i);
  expect(closeButton).toBeInTheDocument();

  fireEvent.click(closeButton);
  
  const orgText = screen.queryByText(/offer.modal.org/i);
  expect(orgText).not.toBeInTheDocument();
});
