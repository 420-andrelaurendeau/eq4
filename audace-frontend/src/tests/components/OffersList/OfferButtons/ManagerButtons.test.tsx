import { render, screen, waitFor } from "@testing-library/react";
import ManagerButtons from "../../../../components/OffersList/OfferRow/OfferButtons/ManagerButtons";
import { OfferStatus } from "../../../../model/offer";
import "@testing-library/jest-dom/extend-expect";
import { offer } from "../OffersListTestUtils";

describe("button displaying", () => {
  it("should display buttons properly", () => {
    render(
      <ManagerButtons
        disabled={false}
        offer={offer}
        updateOffersState={() => {}}
      />
    );

    const acceptButton = screen.getByText(/managerOffersList.acceptButton/i);
    expect(acceptButton).toBeInTheDocument();

    const refuseButton = screen.getByText(/managerOffersList.refuseButton/i);
    expect(refuseButton).toBeInTheDocument();
  });

  it("should display non-pending status and hide buttons", () => {
    const newOffer = { ...offer, offerStatus: OfferStatus.ACCEPTED };

    render(
      <ManagerButtons
        disabled={false}
        offer={newOffer}
        updateOffersState={() => {}}
      />
    );

    const statusText = screen.getByText(/managerOffersList.ACCEPTED/i);
    expect(statusText).toBeInTheDocument();
  });
});

describe("button clicking", () => {
  it("should call updateOffersState with correct status on accept button click", async () => {
    const updateOffersState = jest.fn();

    render(
      <ManagerButtons
        disabled={false}
        offer={offer}
        updateOffersState={updateOffersState}
      />
    );

    const acceptButton = screen.getByText(/managerOffersList.acceptButton/i);
    expect(acceptButton).toBeInTheDocument();

    const refuseButton = screen.getByText(/managerOffersList.refuseButton/i);
    expect(refuseButton).toBeInTheDocument();

    acceptButton.click();
    await waitFor(() =>
      expect(updateOffersState).toHaveBeenCalledWith(
        offer,
        OfferStatus.ACCEPTED
      )
    );
  });

  it("should call updateOffersState with correct status on refuse button click", async () => {
    const updateOffersState = jest.fn();

    render(
      <ManagerButtons
        disabled={false}
        offer={offer}
        updateOffersState={updateOffersState}
      />
    );

    const acceptButton = screen.getByText(/managerOffersList.acceptButton/i);
    expect(acceptButton).toBeInTheDocument();

    const refuseButton = screen.getByText(/managerOffersList.refuseButton/i);
    expect(refuseButton).toBeInTheDocument();

    refuseButton.click();
    await waitFor(() =>
      expect(updateOffersState).toHaveBeenCalledWith(offer, OfferStatus.REFUSED)
    );
  });
});
