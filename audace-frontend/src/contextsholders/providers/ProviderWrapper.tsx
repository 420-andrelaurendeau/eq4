import { ReactNode } from "react";
import { CVProvider } from "./CVContextHolder";
import { ApplicationProvider } from "./ApplicationsContextHolder";
import { SessionProvider } from "./SessionContextHolder";
import { OfferProvider } from "./OfferContextHolder";

interface Props {
  children: ReactNode;
}

const ProviderWrapper = ({ children }: Props) => {
  return (
    <>
      <CVProvider>
        <ApplicationProvider>
          <OfferProvider>
            <SessionProvider>{children}</SessionProvider>
          </OfferProvider>
        </ApplicationProvider>
      </CVProvider>
    </>
  );
};

export default ProviderWrapper;
