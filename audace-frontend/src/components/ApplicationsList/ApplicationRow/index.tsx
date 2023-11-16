import { useTranslation } from "react-i18next";
import { Application, ApplicationStatus } from "../../../model/application";
import { Button, Col } from "react-bootstrap";
import React, { useEffect, useState } from "react";
import CvModal from "../../CVsList/CvRow/CvModal";
import { UserType } from "../../../model/user";
import EmployerButtons from "./ApplicationButtons/EmployerButtons";
import { Contract } from "../../../model/contract";
import { getContractByApplicationIdForStudent, signContractByStudent } from "../../../services/contractService";
import { getUserId } from "../../../services/authService";
import {useNavigate} from "react-router-dom";

interface Props {
  application: Application;
  userType: UserType;
  updateApplicationsState?: (application: Application, applicationStatus: ApplicationStatus) => void;
}

const ApplicationRow = ({ application, userType, updateApplicationsState }: Props) => {
  const { t } = useTranslation();
  const [show, setShow] = useState<boolean>(false);
  const [contract, setContract] = useState<Contract | null>(null);
  const studentId = parseInt(getUserId()!);
  const navigate = useNavigate();

  useEffect(() => {
    if (userType === UserType.Student) {

      const fetchContract = async () => {
        try {
          const res = await getContractByApplicationIdForStudent(application.id!);
          setContract(res.data);
          console.log("Contract : " + res.data.id, res.data.supervisor.firstName);
        } catch (err : any) {
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

  const handleApply = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.stopPropagation();

    if (!contract || !studentId) {
      console.error("Contract or user ID is null");
      return;
    }

    try {
      const response = await signContractByStudent(contract.id!);
      console.log("Contract signed successfully:", response.data);

    } catch (err) {
      console.error("Error signing contract:", err);
    }
  };

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

  return (
    <>
      <tr>
        {userType !== UserType.Employer && <td>{application.offer!.title}</td>}
        {userType !== UserType.Student && <th>{application.cv!.student.firstName} {application.cv!.student.lastName}</th>}
        <td>
          <Col>{application.cv!.fileName}</Col>
          <Col className="text-muted small"><u className="hovered" onClick={handleClick}>{t("cvsList.viewMore")}</u></Col>
        </td>
        {userType !== UserType.Employer && <td>{application.offer!.employer.organisation}</td>}
        <td>
          {
            userType === UserType.Employer && application.offer!.availablePlaces > 0 ? (
                <div className="d-flex justify-content-center">
                  <EmployerButtons application={application} updateApplicationsState={updateApplicationsState} />
                </div>
            ) : (
                userType === UserType.Student && contract !== null ? (
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
                          onClick={() => handleViewContract(contract!.id!)}
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
