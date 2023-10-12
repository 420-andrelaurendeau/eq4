import { UserType } from "../../../../model/user";
import EmployerButtons from "./EmployerButtons";
import StudentButtons from "./StudentButtons";
import ManagerButtons from "./ManagerButtons";
import { Offer, OfferStatus } from "../../../../model/offer";

interface Props {
    userType : UserType;
    disabled? : boolean;
    offer : Offer;
    updateOffersState?: (offer : Offer, offerStatus : OfferStatus) => void;
}

const OfferButtons = ({userType, disabled, offer, updateOffersState} : Props) => {
    const selectButtons = () => {
        switch (userType) {
            case UserType.Student:
                return <StudentButtons disabled={disabled}/>;
            case UserType.Manager:
                return <ManagerButtons disabled={disabled} offer={offer} updateOffersState={updateOffersState}/>;
            case UserType.Employer:
                return <EmployerButtons disabled={disabled} offer={offer}/>;
        }        
    }

    return (
        <>
            {selectButtons()}
        </>
    );
}

export default OfferButtons;