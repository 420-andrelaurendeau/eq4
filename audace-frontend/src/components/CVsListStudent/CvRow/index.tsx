import { Col } from "react-bootstrap";
import { useState } from "react";
import CvModal from "./CvModal";
import { useTranslation } from "react-i18next";
import { useCVContext } from "../../../contextsholders/providers/CVContextHolder";

const CvRow = () => {
  const [show, setShow] = useState<boolean>(false);
  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);
  const { t } = useTranslation();
  const { cvs } = useCVContext();
  const cv = cvs[0];

  return (
    <>
      <tr>
        <td>
          <Col>{cv.fileName}</Col>
          <Col className="text-muted small">
            <u className="hovered" onClick={handleClick}>
              {t("cvsList.viewMore")}
            </u>
          </Col>
        </td>
      </tr>
      {show && <CvModal show={show} handleClose={handleClose} />}
    </>
  );
};

export default CvRow;
