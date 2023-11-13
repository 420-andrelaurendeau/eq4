import { UserType } from "../../../../model/user";
import EmployerButtons from "./EmployerButtons";
import StudentButtons from "./StudentButtons";
import ManagerButtons from "./ManagerButtons";
import { Offer, OfferStatus } from "../../../../model/offer";

interface Props {
  userType: UserType;
  disabled: boolean;
  offer: Offer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  seeApplications?: (offer: Offer) => void;
  hideRow?: () => void;
}
const OfferButtons = ({userType, disabled, offer, updateOffersState, seeApplications, hideRow}: Props) => {
  const selectButtons = () => {
    switch (userType) {
      case UserType.Student:
        return <StudentButtons disabled={false} offer={offer} />;
      case UserType.Manager:
        return (
          <ManagerButtons disabled={false} offer={offer} updateOffersState={updateOffersState}/>
        );
      case UserType.Employer:
        return (
          <EmployerButtons disabled={false} seeApplications={seeApplications} offer={offer} hideRow={hideRow} />
        );
    }
  };

  return(<>{selectButtons()}</>);
};

export default OfferButtons;