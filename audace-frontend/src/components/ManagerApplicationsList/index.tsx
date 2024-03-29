import { Fragment, useState } from "react";
import { Table, Form, Row, Col } from "react-bootstrap";
import Application from "../../model/application";
import { useTranslation } from "react-i18next";
import { useEffect } from "react";
import "./index.css";
import ManagerApplicationRow from "./ManagerApplicationRow";

interface Props {
  applications: Application[];
}

const ManagerApplicationsList = ({ applications }: Props) => {
  const { t } = useTranslation();
  const [searchText, setSearchText] = useState("");
  const [filteredApplications, setFilteredApplications] =
    useState(applications); // Limit to the first 10 rows

  useEffect(() => {
    if (searchText) {
      const searchRegex = new RegExp(searchText, "i");
      const filtered = applications.filter(
        (application) =>
          searchRegex.test(application.offer!.title) ||
          searchRegex.test(application.offer!.employer.organisation) ||
          searchRegex.test(application.cv!.student!.studentNumber) ||
          searchRegex.test(application.cv!.student!.firstName!) ||
          searchRegex.test(application.cv!.student!.lastName!)
      );
      setFilteredApplications(filtered);
    } else {
      setFilteredApplications(applications);
    }
  }, [searchText, applications]);

  return (
    <>
      <Row
        style={{
          padding: "16px 0",
          display: "flex",
          justifyContent: "flex-end",
          alignItems: "center",
        }}
      >
        <Col>
          <h3>{t("applicationsList.acceptedListTitle")}</h3>
        </Col>
        <Col>
          <Form>
            <Form.Group controlId="searchText" style={{ margin: 0 }}>
              <Form.Control
                type="text"
                placeholder={t("applicationsList.SearchPlaceholder")}
                value={searchText}
                onChange={(e) => setSearchText(e.target.value)}
                className="custom-search-input"
              />
            </Form.Group>
          </Form>
        </Col>
      </Row>

      {filteredApplications.length > 0 ? (
        <div style={{ overflow: "auto", maxHeight: "18.5rem" }}>
          <Table className="table-custom" striped bordered hover size="sm">
            <thead className="table-custom">
              <tr>
                <th>{t("applicationsList.offerTitle")}</th>
                <th>{t("applicationsList.organization")}</th>
                <th>{t("cvsList.studentName")}</th>
                <th></th>
              </tr>
            </thead>
            <tbody className="table-custom">
              {filteredApplications.map((application) => (
                <Fragment key={application.id}>
                  <ManagerApplicationRow
                    application={application}
                  />
                </Fragment>
              ))}
            </tbody>
          </Table>
        </div>
      ) : (
        <p>{t("applicationsList.noAcceptedApplications")}</p>
      )}
    </>
  );
};

export default ManagerApplicationsList;
