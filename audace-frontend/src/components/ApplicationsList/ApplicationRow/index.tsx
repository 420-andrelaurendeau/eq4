import { useTranslation } from "react-i18next";
import {Application, ApplicationStatus} from "../../../model/application";
import {Button, Col, Container} from "react-bootstrap";
import {useEffect, useState} from "react";
import CvModal from "../../CVsList/CvRow/CvModal";
import {UserType} from "../../../model/user";
import EmployerButtons from "./ApplicationButtons/EmployerButtons";
import {Contract} from "../../../model/contract";
import {getContractByApplicationId, getContractByApplicationIdForStudent} from "../../../services/contractService";
import {getUserId} from "../../../services/authService";

interface Props {
  application: Application;
  userType: UserType;
  updateApplicationsState?: (application: Application, applicationStatus: ApplicationStatus) => void;
}

const ApplicationRow = ({ application, userType, updateApplicationsState }: Props) => {
  const { t } = useTranslation();
  const [show, setShow] = useState<boolean>(false);
  const [contract, setContract] = useState<Contract>();
  const handleClick = () => setShow(true);
  const handleClose = () => setShow(false);

  useEffect(() => {
    fetchContract().then(r => console.log("Contract fetched"));
  }, []);

  const fetchContract = async () => {
    if (UserType.Student !== userType) {
      return;
    }

    getContractByApplicationIdForStudent(application.id!)
        .then((res) => {
          setContract(res.data);
          console.log("Contract : " + contract?.id! + contract?.supervisor!.firstName!);
        })
        .catch((err) => {
          if (err.response && err.response.status === 404) {
            setContract(undefined);
          } else {
            console.error(err);
          }
        });
  }

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
          {
            userType === UserType.Employer && application.offer!.availablePlaces > 0 ? (
                <div className="d-flex justify-content-center">
                  <EmployerButtons application={application} updateApplicationsState={updateApplicationsState} />
                </div>
            ) : (
                userType === UserType.Student && contract !== undefined ? (
                    <div
                        style={{
                          display: "flex",
                          flexDirection: "column",
                          alignItems: "center",
                          justifyContent: "center",
                        }}
                    >
                      <Button
                          // disabled={isButtonDisabled()}
                          // onClick={handleApply}
                          variant="outline-primary"
                          className="text-dark"
                      >
                        {t("student.signContractButton")}
                      </Button>
                    </div>
                ) : (
                    t(`applicationsList.row.status.${application.applicationStatus}`)
                )
            )
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
