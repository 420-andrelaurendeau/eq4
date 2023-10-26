import { Offer, OfferStatus } from "../../model/offer";
import { useTranslation } from "react-i18next";
import OfferRow from "./OfferRow";
import { UserType } from "../../model/user";
import GenericTable from "../GenericTable";

interface Props {
  offers: Offer[];
  error: string;
  userType: UserType;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
}

const OffersList = ({ offers, error, userType, updateOffersState }: Props) => {
  const { t } = useTranslation();

  return (
    <>
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
            <th></th>
          </tr>
        </thead>
        <tbody>
          {offers.map((offer) => {
            return (
              <OfferRow
                key={offer.id}
                offer={offer}
                userType={userType}
                updateOffersState={updateOffersState}
              />
            );
          })}
        </tbody>
      </GenericTable>
    </>
  );
};

export default OffersList;
