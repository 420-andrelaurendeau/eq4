import { useTranslation } from "react-i18next";
import Application from "../../../model/application";
import { Col } from "react-bootstrap";
import { useState } from "react";
import CvModal from "../../CVsList/CvRow/CvModal";

interface Props {
  application: Application;
}

const ApplicationRow = ({ application }: Props) => {
  const { t } = useTranslation();
  const [show, setShow] = useState<boolean>(false);
  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);

  return (
    <>
      <tr>
        <td>{application.offer!.title}</td>
        <td>
          <Col>{application.cv!.fileName}</Col>
          <Col className="text-muted small">
            <u className="hovered" onClick={handleClick}>
              {t("cvsList.viewMore")}
            </u>
          </Col>
        </td>
        <td>{application.offer!.employer.organisation}</td>
        <td>
          {t(`applicationsList.row.status.${application.applicationStatus}`)}
        </td>
      </tr>
      {show && (
        <CvModal cv={application.cv!} show={show} handleClose={handleClose} />
      )}
    </>
  );
};

export default ApplicationRow;
