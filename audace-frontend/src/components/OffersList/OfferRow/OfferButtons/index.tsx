import { UserType } from "../../../../model/user";
import EmployerButtons from "./EmployerButtons";
import StudentButtons from "./StudentButtons";
import ManagerButtons from "./ManagerButtons";
import { Offer, OfferStatus } from "../../../../model/offer";
import { getUserType } from "../../../../services/authService";
import Application from "../../../../model/application";

interface Props {
  offer: Offer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
  pendingApplications?: Application[];
}
const OfferButtons = ({ offer, updateOffersState, pendingApplications }: Props) => {
  const selectButtons = () => {
    const userType = getUserType();

    switch (userType) {
      case UserType.Student:
        return <StudentButtons offer={offer} />;
      case UserType.Manager:
        return (
          <ManagerButtons
            offer={offer}
            updateOffersState={updateOffersState!}
          />
        );
      case UserType.Employer:
        return <EmployerButtons offer={offer} pendingApplications={pendingApplications!}/>;
    }
  };

  return (<>{selectButtons()}</>);
};

export default OfferButtons;