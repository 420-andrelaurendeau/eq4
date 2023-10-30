import {Alert, Table} from "react-bootstrap";
import ApplicationRow from "../ApplicationsList/ApplicationRow";
import {t} from "i18next";
import Application from "../../model/application";
import {useTranslation} from "react-i18next";

interface Props {
    applications: Application[];
    error: string;
}
const ManagerApplicationsList = ({applications, error}: Props) => {
    const {t} = useTranslation();

    return (
        <>
            {error !== "" ? (
                <Alert variant="danger">{error}</Alert>
            ) : applications.length > 0 ? (
                <>
                    <h2>{t("applicationsList.title")}</h2>
                    <Table striped bordered hover size="sm">
                        <thead>
                        <tr>
                            <th>{t("applicationsList.offerTitle")}</th>
                            <th>{t("applicationsList.organization")}</th>
                            <th>{t("applicationsList.status")}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {applications.map((application) => (
                            <ApplicationRow
                                key={application.id}
                                application={application}
                            />
                        ))}
                        </tbody>
                    </Table>
                </>
            ) : (
                <h2>No applications have been made</h2>
            )}
        </>
    );
};

export default ManagerApplicationsList;