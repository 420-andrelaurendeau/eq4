import { UserType } from "../../../../model/user";
import EmployerButtons from "./EmployerButtons";
import StudentButtons from "./StudentButtons";
import ManagerButtons from "./ManagerButtons";
import { Offer, OfferStatus } from "../../../../model/offer";

interface Props {
  userType: UserType;
  disabled?: boolean;
  offer: Offer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  hideRow?: () => void;
}
const OfferButtons = ({userType, offer, updateOffersState, hideRow}: Props) => {
  const selectButtons = () => {
    switch (userType) {
      case UserType.Student:
        return <StudentButtons offer={offer} />;
      case UserType.Manager:
        return (
          <ManagerButtons offer={offer} updateOffersState={updateOffersState}/>
        );
      case UserType.Employer:
        return (
          <EmployerButtons offer={offer} hideRow={hideRow} />
        );
    }
  };

  return <>{selectButtons()}</>;
};

export default OfferButtons;