import { Dropdown } from "react-bootstrap";
import { Session } from "../../../model/session";
import { formatDate } from "../../../services/formatService";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";

interface Props {
  session: Session;
}

const SelectorOption = ({ session }: Props) => {
  const { chosenSession } = useSessionContext();

  return (
    <Dropdown.Item eventKey={`${session.id}`} active={chosenSession?.id === session.id}>
      Session {formatDate(session.startDate)} - {formatDate(session.endDate)}
    </Dropdown.Item>
  );
};

export default SelectorOption;
