import { useState } from "react";
import { Alert, Table, Form } from "react-bootstrap";
import ApplicationRow from "../ApplicationsList/ApplicationRow";
import Application from "../../model/application";
import { useTranslation } from "react-i18next";
import { useEffect } from "react";

interface Props {
    applications: Application[];
    error: string;
}

const ManagerApplicationsList = ({ applications, error }: Props) => {
    const { t } = useTranslation();
    const [searchText, setSearchText] = useState("");
    const [filteredApplications, setFilteredApplications] = useState(applications);

    useEffect(() => {
        if (searchText) {
            const searchRegex = new RegExp(searchText, "i"); // Case-insensitive search
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
                    <h2>{t("applicationsList.acceptedListTitle")}</h2>

                    {/* Search bar */}
                    <Form>
                        <Form.Group controlId="searchText">
                            <Form.Control
                                type="text"
                                placeholder="Search..."
                                value={searchText}
                                onChange={(e) => setSearchText(e.target.value)}
                            />
                        </Form.Group>
                    </Form>

                    <Table striped bordered hover size="sm">
                        <thead>
                        <tr>
                            <th>{t("applicationsList.offerTitle")}</th>
                            <th>{t("applicationsList.organization")}</th>
                            <th>{t("cvsList.studentName")}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {filteredApplications.map((application) => (
                            <ApplicationRow key={application.id} application={application} />
                        ))}
                        </tbody>
                    </Table>
                </>
            )}
        </>
    );
};

export default ManagerApplicationsList;
