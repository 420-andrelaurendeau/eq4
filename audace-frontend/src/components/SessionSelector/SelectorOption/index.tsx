import { Dropdown } from "react-bootstrap";
import { Session } from "../../../model/session";
import { formatDate } from "../../../services/formatService";

interface Props {
  session: Session;
}

const SelectorOption = ({ session }: Props) => {
  return (
    <Dropdown.Item eventKey={`${session.id}`}>
      Session {formatDate(session.startDate)} - {formatDate(session.endDate)}
    </Dropdown.Item>
  );
};

export default SelectorOption;
