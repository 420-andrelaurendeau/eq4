import { ReactNode, createContext, useContext, useState } from "react";
import { Session } from "../../model/session";

interface SessionProviderProps {
  children: ReactNode;
}

interface SessionContextType {
  sessions: Session[];
  setSessions: (newSessions: Session[]) => void;
}

export const SessionContext = createContext<SessionContextType>({
  sessions: [],
  setSessions: () => {},
});

export const useSessionContext = () => useContext(SessionContext);

export const SessionProvider = ({ children }: SessionProviderProps) => {
  const [sessions, setSessions] = useState<Session[]>([]);

  return (
    <SessionContext.Provider value={{ sessions, setSessions }}>
      {children}
    </SessionContext.Provider>
  );
};
