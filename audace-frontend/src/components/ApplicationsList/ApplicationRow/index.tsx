import { useTranslation } from "react-i18next";
import { Application, ApplicationStatus } from "../../../model/application";
import { Col } from "react-bootstrap";
import { useState } from "react";
import CvModal from "../../CVsList/CvRow/CvModal";
import { UserType } from "../../../model/user";
import EmployerButtons from "./ApplicationButtons/EmployerButtons";
import { getUserType } from "../../../services/authService";
import { Offer } from "../../../model/offer";

interface Props {
  offer?: Offer;
  application: Application;
  updateApplicationsState?: (
    application: Application,
    applicationStatus: ApplicationStatus
  ) => void;
}

const ApplicationRow = ({
  offer,
  application,
  updateApplicationsState,
}: Props) => {
  const { t } = useTranslation();
  const [show, setShow] = useState<boolean>(false);
  const [contract, setContract] = useState<Contract | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (userType === UserType.Student) {

      const fetchContract = async () => {
        try {
          const res = await getContractByApplicationId(application.id!, Authority.STUDENT);
          setContract(res.data);
          console.log("Contract : " + res.data.id, res.data.supervisor.firstName);
        } catch (err: any) {
          if (err.response?.status === 404) {
            setContract(null);
          } else {
            console.error("Error fetching contract:", err);
          }
        }
      };
      fetchContract();
    }
  }, [userType, application.id]);

  const handleViewContract = (contractId: number) => {
    try {
      navigate(`/contract/${contractId}`);
    }
    catch (err) {
      console.error("Error viewing contract:", err);
    }
  };

  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);
  const userType = getUserType();

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
          {userType === UserType.Employer && offer!.availablePlaces > 0 ? (
            <div className="d-flex justify-content-center">
              <EmployerButtons
                application={application}
                updateApplicationsState={updateApplicationsState}
              />
            </div>
          ) : (
            t(`applicationsList.row.status.${application.applicationStatus}`)
          )}
        </td>
      </tr>
      {show && (
        <CvModal cv={application.cv!} show={show} handleClose={handleClose} />
      )}
    </>
  );
};

export default ApplicationRow;
