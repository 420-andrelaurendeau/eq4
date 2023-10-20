import { Alert, Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { UserType } from "../../model/user";
import { CV, CVStatus } from "../../model/cv";
import CvRow from "./CvRow";

interface Props {
    cvs: CV[];
    error: string;
    userType: UserType;
    updateCvsState?: (cv : CV, cvStatus : CVStatus) => void;
}

const CvsList = ({cvs, error, userType, updateCvsState}: Props) => {
    const {t} = useTranslation();

    return (
        <>
            {
                error !== "" 
                ? 
                <Alert variant="danger">{error}</Alert> 
                : 
                cvs.length > 0
                    ?
                    <Table striped bordered size="sm">
                        <thead>
                            <tr>
                                <th>{t("cvsList.studentName")}</th>
                                <th>{t("cvsList.name")}</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {cvs.map((cv) => {return <CvRow key={cv.id} cv={cv} userType={userType} updateCvsState={updateCvsState}/>})}
                        </tbody>
                    </Table>
                    :
                    <p>{t("cvsList.noCvs")}</p>
            }
        </>
    )
}

export default CvsList;