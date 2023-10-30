import { useState } from "react";
import { Alert, Table, Form } from "react-bootstrap";
import ApplicationRow from "../ApplicationsList/ApplicationRow";
import Application from "../../model/application";
import { useTranslation } from "react-i18next";
import { useEffect } from "react";
import './index.css';

interface Props {
    applications: Application[];
    error: string;
}

const ManagerApplicationsList = ({ applications, error }: Props) => {
    const { t } = useTranslation();
    const [searchText, setSearchText] = useState("");
    const [filteredApplications, setFilteredApplications] = useState(applications.slice(0, 10)); // Limit to the first 10 rows

    // Filter applications based on search input
    useEffect(() => {
        if (searchText) {
            const searchRegex = new RegExp(searchText, "i");
            const filtered = applications.filter((application) =>
                searchRegex.test(application.offer!.title) ||
                searchRegex.test(application.offer!.employer.organisation) ||
                searchRegex.test(application.student!.studentNumber) ||
                searchRegex.test(application.student!.firstName!) ||
                searchRegex.test(application.student!.lastName!)
            );
            setFilteredApplications(filtered);
        } else {
            setFilteredApplications(applications);
        }
    }, [searchText, applications]);

    return (
        <>
            {error !== "" ? (
                <Alert variant="danger">{error}</Alert>
            ) : (
                <>
                    <div className={"row"} style={{ padding: "16px 0", display: "flex", justifyContent: "flex-end", alignItems: "center" }}>
                        <h3 className={"col"}>{t("applicationsList.acceptedListTitle")}</h3>
                        <Form className={"col"}>
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
                    </div>

                    {filteredApplications.length > 0 ? (
                        <div style={{ overflow: "auto", maxHeight: "18.5rem" }}>
                            <Table className="table-custom" striped bordered hover size="sm">
                                <thead className="table-custom">
                                <tr>
                                    <th>{t("applicationsList.offerTitle")}</th>
                                    <th>{t("applicationsList.organization")}</th>
                                    <th>{t("cvsList.studentName")}</th>
                                </tr>
                                </thead>
                                <tbody className="table-custom">
                                {filteredApplications.map((application) => (
                                    <ApplicationRow key={application.id} application={application} />
                                ))}
                                </tbody>
                            </Table>
                        </div>
                    ) : (
                        <p>{t("applicationsList.noAcceptedApplications")}</p>
                    )}
                </>
            )}
        </>
    );
};

export default ManagerApplicationsList;
