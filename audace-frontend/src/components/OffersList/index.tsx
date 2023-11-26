import { Offer, OfferStatus } from "../../model/offer";
import { useTranslation } from "react-i18next";
import OfferRow from "./OfferRow";
import GenericTable from "../GenericTable";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import Accordion from "react-bootstrap/Accordion";
import Applications from "../Applications";
import { Card } from "react-bootstrap";
import { getUserType } from "../../services/authService";
import { UserType } from "../../model/user";

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
  const { chosenSession, currentSession, nextSession } = useSessionContext();

  return (
    <>
      <Accordion defaultActiveKey="0">
        <GenericTable
          list={offers}
          error={error}
          emptyListMessage="offersList.noOffers"
          title="studentOffersList.viewTitle"
        >
          <thead>
            <tr>
              <th>{t("offersList.title")}</th>
              <th>{t("offersList.internshipStartDate")}</th>
              <th>{t("offersList.internshipEndDate")}</th>
              <th>{t("offersList.availablePlaces")}</th>
              {chosenSession?.id === nextSession?.id && <th></th>}
            </tr>
          </thead>
          <tbody>
            {offers.map((offer: Offer) => {
              return (
                <>
                  <OfferRow
                    key={offer.id}
                    offer={offer}
                    updateOffersState={updateOffersState}
                    updateAvailablePlaces={updateAvailablePlaces}
                  />
                </>
              );
            })}
          </tbody>
        </GenericTable>
      </Accordion>
    </>
  );
};

export default OffersList;
