import { Accordion, Card, Placeholder } from "react-bootstrap";
import { Employer, Student } from "../../model/user";
import { useTranslation } from "react-i18next";

interface CompanyInfoCardViewProps {
  employer: Employer;
}

const CompanyInfoCardView = ({ employer }: CompanyInfoCardViewProps) => {
  const { t } = useTranslation();
  return (
    <Accordion defaultActiveKey={['0']} alwaysOpen className="shadow-sm">
      <Accordion.Item eventKey="0">
        <Accordion.Header>
          {employer && <Card.Title>{t("infoCard.employer.title")} <i>{employer?.organisation}</i></Card.Title>}
        </Accordion.Header>
        <Accordion.Body>
          {employer ? (
            <Card.Body>
              <Card.Text>
                <b>{t("infoCard.employer.name")}:</b> {employer.firstName} {employer.lastName}
              </Card.Text>
              <Card.Text>
                <b>{t("infoCard.employer.email")}:</b> {employer.email}
              </Card.Text>
              <Card.Text>
                <b>{t("infoCard.employer.phone")}:</b> {employer.phone} Ext: {employer.extension}
              </Card.Text>
              <Card.Text>
                <b>{t("infoCard.employer.address")}:</b> {employer.address}
              </Card.Text>
            </Card.Body>
          ) : (
            <Card.Body>
              <Placeholder as={Card.Title} animation="glow">
                <Placeholder xs={6} />
              </Placeholder>
              <Placeholder as={Card.Text} animation="glow">
                <Placeholder xs={7} /> <Placeholder xs={4} /> <Placeholder xs={4} /> <Placeholder xs={6} /> <Placeholder xs={8} />
              </Placeholder>
            </Card.Body>
          )}
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
  );
}

interface StudentInfoCardViewProps {
  student: Student;
}

const StudentInfoCardView = ({ student }: StudentInfoCardViewProps) => {
  const { t } = useTranslation();
  return (
    <Accordion defaultActiveKey={['0']} alwaysOpen className="shadow-sm">
      <Accordion.Item eventKey="0">
        <Accordion.Header>
          {student && <Card.Title>{t("infoCard.student.title")} <i>{student?.firstName} {student?.lastName}</i></Card.Title>}
        </Accordion.Header>
        <Accordion.Body>
          {student ? (
            <Card.Body>
              <Card.Text>
                <b>{t("infoCard.student.email")}:</b> {student.email}
              </Card.Text>
              <Card.Text>
                <b>{t("infoCard.student.phone")}:</b> {student.phone}
              </Card.Text>
              <Card.Text>
                <b>{t("infoCard.student.address")}:</b> {student.address}
              </Card.Text>
            </Card.Body>
          ) : (
            <Card.Body>
              <Placeholder as={Card.Title} animation="glow">
                <Placeholder xs={6} />
              </Placeholder>
              <Placeholder as={Card.Text} animation="glow">
                <Placeholder xs={7} /> <Placeholder xs={4} /> <Placeholder xs={4} /> <Placeholder xs={6} /> <Placeholder xs={8} />
              </Placeholder>
            </Card.Body>
          )}
        </Accordion.Body>
      </Accordion.Item>
    </Accordion>
  );
}

interface InfoCardViewProps {
  employer?: Employer;
  student?: Student;
}

const InfoCardView = ({ employer, student }: InfoCardViewProps) => {
  return (
    <>
      {employer ? (
        <CompanyInfoCardView employer={employer as Employer} />
      ) : (
        <StudentInfoCardView student={student as Student} />
      )}
    </>
  );
}

export default InfoCardView;