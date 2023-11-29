import { useTranslation } from "react-i18next";
import { Application, ApplicationStatus } from "../../../model/application";
import { Button, Col } from "react-bootstrap";
import { useEffect, useState } from "react";
import CvModal from "../../CVsList/CvRow/CvModal";
import { UserType } from "../../../model/user";
import EmployerButtons from "./ApplicationButtons/EmployerButtons";
import { getUserType } from "../../../services/authService";
import { Contract } from "../../../model/contract";
import { useNavigate } from "react-router-dom";
import { getContractByApplicationId } from "../../../services/contractService";
import { Authority } from "../../../model/auth";
import { Offer } from "../../../model/offer";

interface Props {
  application: Application;
  offer?: Offer;
  updateApplicationsState?: (
    application: Application,
    applicationStatus: ApplicationStatus
  ) => void;
}

const ApplicationRow = ({
  application,
  updateApplicationsState,
  offer,
}: Props) => {
  const { t } = useTranslation();
  const [show, setShow] = useState<boolean>(false);
  const [contract, setContract] = useState<Contract | null>(null);
  const navigate = useNavigate();
  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);
  const userType = getUserType();

  useEffect(() => {
    const fetchContract = async () => {
      try {
        const res = await getContractByApplicationId(
          application.id!,
          userType === UserType.Employer
            ? Authority.EMPLOYER
            : Authority.STUDENT
        );
        setContract(res.data);
      } catch (err: any) {
        if (err.response?.status === 404) {
          setContract(null);
        } else {
          console.error("Error fetching contract:", err);
        }
      }
    };
    fetchContract();
  }, [userType, application.id]);

  const handleViewContract = (contractId: number) => {
    try {
      navigate(`/contract/${contractId}`);
    } catch (err) {
      console.error("Error viewing contract:", err);
    }
  };

  const renderStatus = () => {
    if (
      userType === UserType.Employer &&
      application.applicationStatus === ApplicationStatus.PENDING
    ) {
      return (
        <EmployerButtons
          application={application}
          offer={offer!}
          updateApplicationsState={updateApplicationsState}
        />
      );
    }

    if (application.applicationStatus !== ApplicationStatus.ACCEPTED) {
      return t(`employerApplicationsList.${application.applicationStatus}`);
    }

    return renderStatusWithContract();
  };

  const renderStatusWithContract = () => {
    return contract ? (
      <Button
        onClick={() => handleViewContract(contract!.id!)}
        variant="outline-primary"
        className="text-dark"
      >
        {t("student.viewContractDetails")}
      </Button>
    ) : (
      t("student.contractNotAvailable")
    );
  };

  return (
    <>
      <tr>
        {userType !== UserType.Employer && <td>{application.offer!.title}</td>}
        {userType !== UserType.Student && (
          <th>
            {application.cv!.student.firstName}{" "}
            {application.cv!.student.lastName}
          </th>
        )}
        <td>
          <Col>{application.cv!.fileName}</Col>
          <Col className="text-muted small">
            <u className="hovered" onClick={handleClick}>
              {t("cvsList.viewMore")}
            </u>
          </Col>
        </td>
        {userType !== UserType.Employer && (
          <td>{application.offer!.employer.organisation}</td>
        )}
        <td>
          <div className="d-flex justify-content-center">{renderStatus()}</div>
        </td>
      </tr>
      {show && (
        <CvModal cv={application.cv!} show={show} handleClose={handleClose} />
      )}
    </>
  );
};

export default ApplicationRow;
