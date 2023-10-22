import {createContext, ReactNode, useContext, useState} from "react";
import {CV} from "../model/cv";

interface CVProviderProps {
    children: ReactNode;
}
export const CVContext = createContext({
    cv: {} as CV,
    setCv: (newCv: CV) => {}
});

export const useCVContext = () => useContext(CVContext);

export function CVProvider({ children }: CVProviderProps) {
    const [cv, setCv] = useState<CV>({} as CV);

    return (
        <CVContext.Provider value={{cv, setCv}}>
            {children}
        </CVContext.Provider>
    );
}