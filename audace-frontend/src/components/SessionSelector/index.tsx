import { Dropdown } from "react-bootstrap";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import SelectorOption from "./SelectorOption";
import { formatSessionDate } from "../../services/formatService";
import CustomMenu from "./CustomMenu";
import CustomToggle from "./CustomToggle";

const SessionSelector = () => {
  const { chosenSession, setChosenSession, sessions } = useSessionContext();

  const handleSelect = (e: string | null) => {
    if (e === null) return;

    const id = parseInt(e);
    const session = sessions.find((session) => session.id === id);

    if (session === undefined) return;

    setChosenSession(session);
  };

  const determineTitle = () => {
    if (chosenSession === undefined) return "Select a session";

    return formatSessionDate(chosenSession);
  };

  return (
    <>
      <Dropdown className="text-end" onSelect={handleSelect}>
        <Dropdown.Toggle as={CustomToggle} id="session-dropdown">
          {determineTitle()}
        </Dropdown.Toggle>
        <Dropdown.Menu as={CustomMenu}>
          {sessions.map((session, index) => (
            <SelectorOption session={session} key={index} />
          ))}
        </Dropdown.Menu>
      </Dropdown>
    </>
  );
};

export default SessionSelector;
