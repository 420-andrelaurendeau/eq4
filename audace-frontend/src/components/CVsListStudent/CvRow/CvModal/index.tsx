import { Modal } from "react-bootstrap";
import PDFViewer from "../../../PDFViewer";
import { useCVContext } from "../../../../contextsholders/providers/CVContextHolder";

interface Props {
  show: boolean;
  handleClose: () => void;
}

const CvModal = ({ show, handleClose }: Props) => {
  const { cvs } = useCVContext();
  const cv = cvs[0];

  return (
    <>
      <Modal show={show} onHide={handleClose} size="lg">
        <Modal.Header closeButton>
          {cv.student.firstName} {cv.student.lastName} - {cv.fileName}
        </Modal.Header>
        <Modal.Body>
          <PDFViewer pdf={cv.content} />
        </Modal.Body>
      </Modal>
    </>
  );
};

export default CvModal;
