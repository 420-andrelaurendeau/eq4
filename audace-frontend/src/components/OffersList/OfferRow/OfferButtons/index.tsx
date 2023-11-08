import { UserType } from "../../../../model/user";
import EmployerButtons from "./EmployerButtons";
import StudentButtons from "./StudentButtons";
import ManagerButtons from "./ManagerButtons";
import { Offer, OfferStatus } from "../../../../model/offer";
import { getUserType } from "../../../../services/authService";

interface Props {
  disabled: boolean;
  offer: Offer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  seeApplications?: (offer: Offer) => void;
}
const OfferButtons = ({
  disabled,
  offer,
  updateOffersState,
  seeApplications,
}: Props) => {
  const selectButtons = () => {
    const userType = getUserType();

    switch (userType) {
      case UserType.Student:
        return <StudentButtons disabled={disabled} offer={offer} />;
      case UserType.Manager:
        return (
          <ManagerButtons
            disabled={disabled}
            offer={offer}
            updateOffersState={updateOffersState}
          />
        );
      case UserType.Employer:
        return (
          <EmployerButtons
            disabled={disabled}
            seeApplications={seeApplications}
            offer={offer}
          />
        );
    }
  };

  return <>{selectButtons()}</>;
};

export default OfferButtons;
