import { UserType } from "../../../model/user";
import { CV, CVStatus } from "../../../model/cv";
import { Col } from "react-bootstrap";
import CvButtons from "./CvButtons";
import { useState } from "react";
import CvModal from "./CvModal";
import { useTranslation } from "react-i18next";
import { getUserType } from "../../../services/authService";

interface Props {
  cv: CV;
  updateCvsState?: (cv: CV, cvStatus: CVStatus) => void;
}

const CvRow = ({ cv, updateCvsState }: Props) => {
  const [show, setShow] = useState<boolean>(false);
  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);
  const { t } = useTranslation();
  const userType = getUserType();

  return (
    <>
      <tr>
        {userType !== UserType.Student && (
          <td>
            {cv.student.firstName} {cv.student.lastName}
          </td>
        )}
        <td>
          <Col>{cv.fileName}</Col>
          <Col className="text-muted small">
            <u className="hovered" onClick={handleClick}>
              {t("cvsList.viewMore")}
            </u>
          </Col>
        </td>
        {userType !== UserType.Student && (
          <td className="text-center">
            <CvButtons cv={cv} updateCvsState={updateCvsState} />
          </td>
        )}
      </tr>
      {show && <CvModal cv={cv} show={show} handleClose={handleClose} />}
    </>
  );
};

export default CvRow;
