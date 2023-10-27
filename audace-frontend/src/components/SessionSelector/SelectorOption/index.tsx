import { Dropdown } from "react-bootstrap";
import { Session } from "../../../model/session";
import { formatSessionDate } from "../../../services/formatService";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";

interface Props {
  session: Session;
}

const SelectorOption = ({ session }: Props) => {
  const { chosenSession } = useSessionContext();

  return (
    <Dropdown.Item
      eventKey={`${session.id}`}
      active={chosenSession?.id === session.id}
    >
      {formatSessionDate(session)}
    </Dropdown.Item>
  );
};

export default SelectorOption;
