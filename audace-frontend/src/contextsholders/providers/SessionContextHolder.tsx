import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";
import { Session } from "../../model/session";
import {
  getAllSessions,
  getCurrentSession, getNextSession,
  getSessionById,
} from "../../services/sessionService";

const CHOSEN_SESSION_PATH = "chosenSession";

interface SessionProviderProps {
  children: ReactNode;
}

interface SessionContextType {
  sessions: Session[];
  chosenSession?: Session;
  currentSession?: Session;
  nextSession?: Session;
  setChosenSession: (session: Session) => void;
  setSessions: (newSessions: Session[]) => void;
}

export const SessionContext = createContext<SessionContextType>({
  sessions: [],
  chosenSession: undefined,
  currentSession: undefined,
  nextSession: undefined,
  setChosenSession: () => {},
  setSessions: () => {},
});

export const useSessionContext = () => useContext(SessionContext);

export const SessionProvider = ({ children }: SessionProviderProps) => {
  const [sessions, setSessions] = useState<Session[]>([]);
  const [chosenSession, setChosenSession] = useState<Session>();
  const [currentSession, setCurrentSession] = useState<Session>();
  const [nextSession, setNextSession] = useState<Session>();

  const changeChosenSession = (session: Session) => {
    setChosenSession(session);
    localStorage.setItem(CHOSEN_SESSION_PATH, `${session.id}`);
  };

  useEffect(() => {
    if (chosenSession !== undefined) return;
    if (currentSession !== undefined) return;

    const chosenSessionId = localStorage.getItem(CHOSEN_SESSION_PATH);

    if (chosenSessionId) {
      getSessionById(parseInt(chosenSessionId))
        .then((res) => {
          changeChosenSession(res.data);
        })
        .catch((err) => {
          console.error(err);
        });
    }

    getCurrentSession()
      .then((res) => {
        setCurrentSession(res.data);

        if (chosenSessionId !== null) return;

        changeChosenSession(res.data);
      })
      .catch((err) => {
        console.error(err);
      });

    getNextSession()
        .then((res) => {
          setNextSession(res.data);

          if (chosenSessionId !== null) return;

          changeChosenSession(res.data);
        })
        .catch((err) => {
          console.error(err);
        });

  }, [chosenSession, currentSession, nextSession]);



  useEffect(() => {
    getAllSessions()
      .then((res) => {
        setSessions(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  }, []);

  return (
    <SessionContext.Provider
      value={{
        sessions,
        setSessions,
        setChosenSession: changeChosenSession,
        chosenSession,
        currentSession,
        nextSession
      }}
    >
      {children}
    </SessionContext.Provider>
  );
};
