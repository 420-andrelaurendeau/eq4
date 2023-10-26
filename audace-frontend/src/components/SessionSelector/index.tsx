import { DropdownButton } from "react-bootstrap";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import SelectorOption from "./SelectorOption";
import { formatDate } from "../../services/formatService";

const SessionSelector = () => {
  const { currentSession, setCurrentSession, sessions } = useSessionContext();

  const handleSelect = (e: string | null) => {
    if (e === null) return;

    const id = parseInt(e);
    const session = sessions.find((session) => session.id === id);

    if (session === undefined) return;

    setCurrentSession(session);
  };

  const determineTitle = () => {
    if (currentSession === undefined) return "Select a session";

    return `Session ${formatDate(currentSession.startDate)} - ${formatDate(
      currentSession.endDate
    )}`;
  };

  return (
    <>
      <DropdownButton
        className="text-end mb-3"
        title={determineTitle()}
        onSelect={handleSelect}
        variant="secondary"
      >
        {sessions.map((session) => (
          <SelectorOption session={session} key={session.id} />
        ))}
      </DropdownButton>
    </>
  );
};

export default SessionSelector;
