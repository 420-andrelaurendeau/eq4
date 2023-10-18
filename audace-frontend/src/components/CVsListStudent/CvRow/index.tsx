import { UserType } from "../../../model/user";
import { CV } from "../../../model/cv";
import { Col } from "react-bootstrap";
import { useState } from "react";
import CvModal from "./CvModal";
import { useTranslation } from "react-i18next";

interface Props {
    cv: CV;
    userType: UserType;
}

const CvRow = ({cv, userType}: Props) => {
    const [show, setShow] = useState<boolean>(false);
    const handleClick = () => setShow(true);
    const handleClose = () => setShow(false);
    const {t} = useTranslation();

    return (
        <>
            <tr>
                <td>
                    <Col>
                        {cv.fileName}
                    </Col>
                    <Col className="text-muted small">
                        <u className="hovered" onClick={handleClick}>{t("cvsList.viewMore")}</u>
                    </Col>
                </td>
            </tr>
            {show && <CvModal cv={cv} show={show} handleClose={handleClose}/>}
        </>
    );
};

export default CvRow;