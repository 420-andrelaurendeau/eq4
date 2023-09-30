import { UserType } from "../../../../model/user";
import EmployerButtons from "./EmployerButtons";
import StudentButtons from "./StudentButtons";
import ManagerButtons from "./ManagerButtons";

interface Props {
    userType : UserType;
    disabled? : boolean;
}

const OfferButtons = ({userType, disabled} : Props) => {
    const selectButtons = () => {
        switch (userType) {
            case UserType.Student:
                return <StudentButtons disabled={disabled}/>;
            case UserType.Manager:
                return <ManagerButtons disabled={disabled}/>;
            case UserType.Employer:
                return <EmployerButtons disabled={disabled}/>;
        }        
    }

    return (
        <>
            {selectButtons()}
        </>
    );
}

export default OfferButtons;