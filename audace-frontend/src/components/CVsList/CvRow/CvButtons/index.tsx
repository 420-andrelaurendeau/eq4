import { UserType } from "../../../../model/user";
import { CV, CVStatus } from "../../../../model/cv";
import ManagerButtons from "./ManagerButtons";

interface Props {
    userType: UserType;
    disabled?: boolean;
    cv: CV;
    updateCvsState?: (cv: CV, cvStatus: CVStatus) => void;
}

const CvButtons = ({ userType, disabled, cv, updateCvsState }: Props) => {
    const selectButtons = () => {
        switch (userType) {
            case UserType.Manager:
                return <ManagerButtons disabled={disabled} cv={cv} updateCvsState={updateCvsState} />;
        }
    }

    return (
        <>
            {selectButtons()}
        </>
    );
}

export default CvButtons;