import { Dropdown } from "react-bootstrap";
import { useSessionContext } from "../../contextsholders/providers/SessionContextHolder";
import SelectorOption from "./SelectorOption";
import CustomMenu from "./CustomMenu";
import CustomToggle from "./CustomToggle";
import { Offer } from "../../model/offer";
import { useTranslation } from "react-i18next";

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

  const determineSessionSeason = (): string => {
    const startDate = new Date(chosenSession!.startDate);

    if (isFall(startDate)) return "sessionSelector.fall";

    return isWinter(startDate)
      ? "sessionSelector.winter"
      : "sessionSelector.summer";
  };

  const isWinter = (startDate: Date): boolean => {
    const month = startDate.getMonth();

    if (month > 2 && month < 11) return false;
    if (month === 11) return startDate.getDate() >= 21;
    if (month === 2) return startDate.getDate() <= 20;

    return true;
  };

  const isFall = (startDate: Date): boolean => {
    const month = startDate.getMonth();

    if (month < 8 || month > 11) return false;
    if (month === 11) return startDate.getDate() < 21;
    if (month === 8) return startDate.getDate() >= 23;

    return true;
  };

  const getEndDateYear = (): number => {
    const endDate = new Date(chosenSession!.endDate);

    return endDate.getFullYear();
  };

  return (
    <>
      <Dropdown className="text-end" onSelect={handleSelect}>
        <Dropdown.Toggle as={CustomToggle} id="session-dropdown">
          {chosenSession !== undefined
            ? `${t(determineSessionSeason())} ${getEndDateYear()}`
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
