import { UserType } from "../../../../model/user";
import ManagerButtons from "./ManagerButtons";
import { CV, CVStatus } from "../../../../model/cv";

interface Props {
    userType : UserType;
    disabled? : boolean;
    cv : CV;
    updateCvsState?: (cv : CV, cvStatus : CVStatus) => void;
}

const CvButtons = ({userType, disabled, cv, updateCvsState} : Props) => {
    const selectButtons = () => {
        switch (userType) {
            case UserType.Manager:
                return <ManagerButtons disabled={disabled} cv={cv} updateCvsState={updateCvsState}/>;
        }
    }

    return (
        <>
            {selectButtons()}
        </>
    );
}

export default CvButtons;