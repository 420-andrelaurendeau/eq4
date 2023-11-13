import { useTranslation } from "react-i18next";
import {Application, ApplicationStatus} from "../../../model/application";
import { Col } from "react-bootstrap";
import { useState } from "react";
import CvModal from "../../CVsList/CvRow/CvModal";
import {UserType} from "../../../model/user";
import EmployerButtons from "./ApplicationButtons/EmployerButtons";
import {Offer} from "../../../model/offer";

interface Props {
  offer: Offer;
  application: Application;
  userType: UserType;
  updateApplicationsState?: (application: Application, applicationStatus: ApplicationStatus) => void;
}

const ApplicationRow = ({offer, application, userType, updateApplicationsState }: Props) => {
  const { t } = useTranslation();
  const [show, setShow] = useState<boolean>(false);
  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);

  return (
    <>
      <tr>
        {userType != UserType.Employer && <td>{application.offer!.title}</td>}
        {userType != UserType.Student && <th>{application.cv!.student.firstName} {application.cv!.student.lastName}</th>}
        <td>
          <Col>{application.cv!.fileName}</Col>
          <Col className="text-muted small"><u className="hovered" onClick={handleClick}>{t("cvsList.viewMore")}</u></Col>
        </td>
        {userType != UserType.Employer && <td>{application.offer!.employer.organisation}</td>}
        <td>
          {userType === UserType.Employer && offer.availablePlaces > 0 ?  (
              <div className="d-flex justify-content-center">
                <EmployerButtons application={application} updateApplicationsState={updateApplicationsState} />
              </div>
          ):
              t(`applicationsList.row.status.${application.applicationStatus}`)
          }
        </td>
      </tr>
      {show && (
        <CvModal cv={application.cv!} show={show} handleClose={handleClose} />
      )}
    </>
  );
};

export default ApplicationRow;
