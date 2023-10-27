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
  getCurrentSession,
  getSessionById,
} from "../../services/sessionService";

const CURRENT_SESSION_PATH = "currentSession";

interface SessionProviderProps {
  children: ReactNode;
}

interface SessionContextType {
  sessions: Session[];
  currentSession?: Session;
  setCurrentSession: (session: Session) => void;
  setSessions: (newSessions: Session[]) => void;
}

export const SessionContext = createContext<SessionContextType>({
  sessions: [],
  currentSession: undefined,
  setCurrentSession: () => {},
  setSessions: () => {},
});

export const useSessionContext = () => useContext(SessionContext);

export const SessionProvider = ({ children }: SessionProviderProps) => {
  const [sessions, setSessions] = useState<Session[]>([]);
  const [currentSession, setCurrentSession] = useState<Session>();

  const changeCurrentSession = (session: Session) => {
    setCurrentSession(session);
    localStorage.setItem(CURRENT_SESSION_PATH, `${session.id}`);
  };

  useEffect(() => {
    const currentSessionId = localStorage.getItem(CURRENT_SESSION_PATH);

    if (currentSessionId) {
      getSessionById(parseInt(currentSessionId))
        .then((res) => {
          changeCurrentSession(res.data);
        })
        .catch((err) => {
          console.error(err);
        });

      return;
    }

    getCurrentSession()
      .then((res) => {
        changeCurrentSession(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  }, []);

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
        setCurrentSession: changeCurrentSession,
        currentSession,
      }}
    >
      {children}
    </SessionContext.Provider>
  );
};
