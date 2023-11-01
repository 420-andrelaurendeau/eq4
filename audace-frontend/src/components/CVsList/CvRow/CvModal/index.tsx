import {Modal} from "react-bootstrap";
import { CV } from "../../../../model/cv";
import PDFViewer from "../../../PDFViewer";

interface Props {
    cv: CV;
    show: boolean;
    handleClose: () => void;
}

const CvModal = ({cv, show, handleClose}: Props) => {

    return (
        <>
            <Modal show={show} onHide={handleClose} size="lg">
                <Modal.Header closeButton>
                    {cv.student.firstName} {cv.student.lastName} - {cv.fileName}
                </Modal.Header>
                <Modal.Body>
                    <PDFViewer pdf={cv.content}/>
                </Modal.Body>
            </Modal>
        </>
    );
};

export default CvModal;