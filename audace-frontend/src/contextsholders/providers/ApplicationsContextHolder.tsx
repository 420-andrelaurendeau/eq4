import { ReactNode, createContext, useContext, useState } from "react";
import Application from "../../model/application";

interface Props {
  children: ReactNode;
}

interface ApplicationContextType {
  applications: Application[];
  setApplications: (newApplications: Application[]) => void;
}

export const ApplicationContext = createContext<ApplicationContextType>({
  applications: [],
  setApplications: () => {},
});

export const useApplicationContext = () => useContext(ApplicationContext);

export function ApplicationProvider({ children }: Props) {
  const [applications, setApplications] = useState<Application[]>([]);

  return (
    <ApplicationContext.Provider value={{ applications, setApplications }}>
      {children}
    </ApplicationContext.Provider>
  );
}
