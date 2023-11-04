import { Dropdown } from "react-bootstrap";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import SelectorOption from "./SelectorOption";
import CustomMenu from "./CustomMenu";
import CustomToggle from "./CustomToggle";
import { Offer } from "../../model/offer";
import { useTranslation } from "react-i18next";
import { determineSessionSeason, getEndDateYear } from "../../services/formatService";

interface Props {
  seeApplications?: (offer: Offer) => void;
}

const SessionSelector = ({ seeApplications }: Props) => {
  const { chosenSession, setChosenSession, sessions } = useSessionContext();
  const { t } = useTranslation();

  const handleSelect = (e: string | null) => {
    if (e === null) return;

    const id = parseInt(e);
    const session = sessions.find((session) => session.id === id);

    if (session === undefined) return;

    setChosenSession(session);
    if (seeApplications !== undefined) seeApplications(undefined!);
  };

  return (
    <>
      <Dropdown className="text-end" onSelect={handleSelect}>
        <Dropdown.Toggle as={CustomToggle} id="session-dropdown">
          {chosenSession !== undefined
            ? `${t(determineSessionSeason(chosenSession))} ${getEndDateYear(chosenSession)}`
            : t("sessionSelector.selectSession")}
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
