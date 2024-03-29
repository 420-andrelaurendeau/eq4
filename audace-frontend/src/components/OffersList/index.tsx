import { Offer, OfferStatus } from "../../model/offer";
import { useTranslation } from "react-i18next";
import OfferRow from "./OfferRow";
import GenericTable from "../GenericTable";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import Accordion from "react-bootstrap/Accordion";

interface Props {
  offers: Offer[];
  error: string;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  updateAvailablePlaces?: (offer: Offer) => void;
}

const OffersList = ({
  offers,
  error,
  updateOffersState,
  updateAvailablePlaces,
}: Props) => {
  const { t } = useTranslation();
  const { chosenSession, currentSession } = useSessionContext();

  return (
    <>
      <Accordion defaultActiveKey="0">
        <GenericTable
          list={offers}
          error={error}
          emptyListMessage="offersList.noOffers"
          title=""
        >
          <thead>
            <tr>
              <th>{t("offersList.title")}</th>
              <th>{t("offersList.internshipStartDate")}</th>
              <th>{t("offersList.internshipEndDate")}</th>
              <th>{t("offersList.availablePlaces")}</th>
              {chosenSession?.id === currentSession?.id && <th></th>}
            </tr>
          </thead>
          <tbody>
            {offers.map((offer: Offer) => {
              return (
                <OfferRow
                  key={offer.id}
                  offer={offer}
                  updateOffersState={updateOffersState}
                  updateAvailablePlaces={updateAvailablePlaces}
                />
              );
            })}
          </tbody>
        </GenericTable>
      </Accordion>
    </>
  );
};

export default OffersList;
