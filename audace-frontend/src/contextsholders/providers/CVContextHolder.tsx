import { createContext, ReactNode, useContext, useState } from "react";
import { CV } from "../../model/cv";

interface CVProviderProps {
  children: ReactNode;
}
interface CVContextType {
  cvs: CV[];
  setCvs: (newCvs: CV[]) => void;
}

export const CVContext = createContext<CVContextType>({
  cvs: [],
  setCvs: () => {},
});

export const useCVContext = () => useContext(CVContext);

export function CVProvider({ children }: CVProviderProps) {
  const [cvs, setCvs] = useState<CV[]>([]);

  return (
    <CVContext.Provider value={{ cvs, setCvs }}>{children}</CVContext.Provider>
  );
}
