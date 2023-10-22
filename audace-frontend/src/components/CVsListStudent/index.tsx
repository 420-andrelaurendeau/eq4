import { Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import CvRow from "./CvRow";
import {useCVContext} from "../../contextsholders/CVContextHolder";

interface Props {
    error: string;
}

const CvsList = ({ error}: Props) => {
    const {t} = useTranslation();
    const {cvs } = useCVContext();

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
                            {cvs.map((cv) => {return <CvRow key={cv.id}/>})}
                        </tbody>
                    </Table>
                    :
                    <p>{t("cvsList.noCvs")}</p>
            }
        </>
    )
}

export default CvsList;