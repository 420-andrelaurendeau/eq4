import { Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { UserType } from "../../model/user";
import { CV } from "../../model/cv";
import CvRow from "./CvRow";

interface Props {
    cvs: CV[];
    error: string;
    userType: UserType;
}

const CvsList = ({cvs, error, userType}: Props) => {
    const {t} = useTranslation();

    return (
        <>
            <h2>CVs</h2>
            {
                error !== ""
                ?
                <p>{error}</p>
                :
                cvs.length > 0
                    ?
                    <Table striped bordered size="sm">
                        <thead>
                            <tr>
                                <th>{t("cvsList.name")}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {cvs.map((cv) => {return <CvRow key={cv.id} cv={cv} userType={userType}/>})}
                        </tbody>
                    </Table>
                    :
                    <p>{t("cvsList.noCvs")}</p>
            }
        </>
    )
}

export default CvsList;