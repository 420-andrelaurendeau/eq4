import { PropsWithChildren } from "react";
import { isConnected } from "../../services/authService";

interface Props {
  isConnectedRoute: boolean;
}

const ConnectedRoute = ({children, isConnectedRoute,}: PropsWithChildren<Props>) => {
  return <>{isConnectedRoute === isConnected() ? <>{children}</> : <></>}</>;
};

export default ConnectedRoute;
