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
} from "../../services/sessionService";

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

  useEffect(() => {
    getCurrentSession()
      .then((res) => {
        setCurrentSession(res.data);
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
      value={{ sessions, setSessions, setCurrentSession, currentSession }}
    >
      {children}
    </SessionContext.Provider>
  );
};
