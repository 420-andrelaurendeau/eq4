import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useState,
} from "react";
import { Session } from "../../model/session";
import { getCurrentSession } from "../../services/sessionService";
import { isConnected } from "../../services/authService";

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
    if (!isConnected()) return;

    getCurrentSession()
      .then((res) => {
        setCurrentSession(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  });

  return (
    <SessionContext.Provider
      value={{ sessions, setSessions, setCurrentSession, currentSession }}
    >
      {children}
    </SessionContext.Provider>
  );
};
