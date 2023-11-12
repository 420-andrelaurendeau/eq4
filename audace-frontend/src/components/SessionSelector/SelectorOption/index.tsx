import { Dropdown } from "react-bootstrap";
import { Session } from "../../../model/session";
import {
  determineSessionSeason,
  getEndDateYear,
} from "../../../services/formatService";
import { useSessionContext } from "../../../contextsholders/providers/SessionContextHolder";
import { useTranslation } from "react-i18next";

interface Props {
  session: Session;
}

const SelectorOption = ({ session }: Props) => {
  const { chosenSession } = useSessionContext();
  const { t } = useTranslation();

  return (
    <Dropdown.Item
      eventKey={`${session.id}`}
      active={chosenSession?.id === session.id}
    >
      {`${t(determineSessionSeason(session))} ${getEndDateYear(session)}`}
    </Dropdown.Item>
  );
};

export default SelectorOption;
