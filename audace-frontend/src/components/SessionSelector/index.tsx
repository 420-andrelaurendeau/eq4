import { DropdownButton } from "react-bootstrap";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import SelectorOption from "./SelectorOption";
import { formatDate } from "../../services/formatService";

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
      <DropdownButton
        className="text-end mb-3"
        title={determineTitle()}
        onSelect={handleSelect}
        variant="outline-dark"
      >
        {sessions.map((session) => (
          <SelectorOption session={session} key={session.id} />
        ))}
      </DropdownButton>
    </>
  );
};

export default SessionSelector;
