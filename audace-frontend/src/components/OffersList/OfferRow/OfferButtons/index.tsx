import { UserType } from "../../../../model/user";
import EmployerButtons from "./EmployerButtons";
import StudentButtons from "./StudentButtons";
import ManagerButtons from "./ManagerButtons";
import { Offer, OfferStatus } from "../../../../model/offer";
import { getUserType } from "../../../../services/authService";

interface Props {
  offer: Offer;
  updateOffersState?: (offer: Offer, offerStatus: OfferStatus) => void;
}
const OfferButtons = ({ offer, updateOffersState }: Props) => {
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
        return <EmployerButtons offer={offer} />;
    }
  };

  return (<>{selectButtons()}</>);
};

export default OfferButtons;