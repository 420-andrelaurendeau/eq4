import { ReactNode } from "react";
import { CVProvider } from "./CVContextHolder";
import { ApplicationProvider } from "./ApplicationsContextHolder";
import { SessionProvider } from "./SessionContextHolder";

interface Props {
  children: ReactNode;
}

const ProviderWrapper = ({ children }: Props) => {
  return (
    <>
      <CVProvider>
        <ApplicationProvider>
          <SessionProvider>{children}</SessionProvider>
        </ApplicationProvider>
      </CVProvider>
    </>
  );
};

export default ProviderWrapper;
