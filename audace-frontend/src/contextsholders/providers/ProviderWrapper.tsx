import { ReactNode } from "react";
import { CVProvider } from "./CVContextHolder";
import { ApplicationProvider } from "./ApplicationsContextHolder";

interface Props {
  children: ReactNode;
}

const ProviderWrapper = ({ children }: Props) => {
  return (
    <>
      <CVProvider>
        <ApplicationProvider>{children}</ApplicationProvider>
      </CVProvider>
    </>
  );
};

export default ProviderWrapper;
