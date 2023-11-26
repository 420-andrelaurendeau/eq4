import { UserType } from "../../../../model/user";
import { CV, CVStatus } from "../../../../model/cv";
import ManagerButtons from "./ManagerButtons";
import { getUserType } from "../../../../services/authService";

interface Props {
  disabled?: boolean;
  cv: CV;
  updateCvsState?: (cv: CV, cvStatus: CVStatus) => void;
}

const CvButtons = ({ disabled, cv, updateCvsState }: Props) => {
  const selectButtons = () => {
    switch (getUserType()) {
      case UserType.Manager:
        return (
          <ManagerButtons
            disabled={disabled}
            cv={cv}
            updateCvsState={updateCvsState}
          />
        );
    }
  };

  return <>{selectButtons()}</>;
};

export default CvButtons;
