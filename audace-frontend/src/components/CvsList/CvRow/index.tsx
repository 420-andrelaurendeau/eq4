import { UserType } from "../../../model/user";
import { CV, CVStatus } from "../../../model/cv";
import { Col } from "react-bootstrap";
import CvButtons from "./CvButtons";
import { useState } from "react";
import CvModal from "./CvModal";
import { useTranslation } from "react-i18next";


interface Props {
    cv: CV;
    userType: UserType;
    updateCvsState?: (cv : CV, cvStatus : CVStatus) => void;
}

const CvRow = ({cv, userType, updateCvsState}: Props) => {
    const [show, setShow] = useState<boolean>(false);
    const handleClick = () => setShow(true);
    const handleClose = () => setShow(false);
    const {t} = useTranslation();

    return (
        <>
            <tr className="hovered">
                <td>
                    {cv.student.firstName} {cv.student.lastName}
                </td>
                <td>
                    <Col>
                        {cv.fileName}
                    </Col>
                    <Col onClick={handleClick} className="text-muted small">
                        <u>{t("cvsList.viewMore")}</u>
                    </Col>
                </td>                
                <td className="text-end"><CvButtons userType={userType} disabled={false} cv={cv} updateCvsState={updateCvsState}/></td>
            </tr>
            {show && <CvModal cv={cv} show={show} handleClose={handleClose}/>}
        </>
    );
};

export default CvRow;