import { Dropdown, DropdownButton } from "react-bootstrap";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import SelectorOption from "./SelectorOption";
import { formatDate } from "../../services/formatService";
import CustomMenu from "./CustomMenu";

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

    return `Session ${formatDate(chosenSession.startDate)} - ${formatDate(
      chosenSession.endDate
    )}`;
  };

  return (
    <>
      <Dropdown>
        <Dropdown.Toggle></Dropdown.Toggle>
        <Dropdown.Menu as={CustomMenu}>
          {sessions.map((session, index) => (
            <SelectorOption session={session} key={index} />
          ))}
        </Dropdown.Menu>
      </Dropdown>

      {/* <DropdownButton
        className="mb-3 text-end"
        title={determineTitle()}
        onSelect={handleSelect}
        variant="outline-dark"
      >
        {sessions.map((session) => (
          <SelectorOption session={session} key={session.id} />
        ))}
      </DropdownButton> */}
    </>
  );
};

export default SessionSelector;
