import { PropsWithChildren } from "react";
import { isConnected } from "../../services/authService";
import PageNotFoundView from "../../views/PageNotFoundView";

interface Props {
  isConnectedRoute: boolean;
}

const ConnectedRoute = ({children, isConnectedRoute,}: PropsWithChildren<Props>) => {
  return <>{isConnectedRoute === isConnected() ? <>{children}</> : <PageNotFoundView />}</>;
};

export default ConnectedRoute;
