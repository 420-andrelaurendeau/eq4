import { PropsWithChildren, useEffect, useState } from "react";
import { getAuthority } from "../../services/authService";
import ConnectedRoute from "../ConnectedRoute";
import PageNotFoundView from "../../views/PageNotFoundView";

interface Props {
  requiredAuthority: string;
}

const AuthorizedRoute = ({
  children,
  requiredAuthority,
}: PropsWithChildren<Props>) => {
  const [isAuthorized, setIsAuthorized] = useState<boolean>(false);

  useEffect(() => {
    let userAuthority = getAuthority();

    if (!userAuthority) return;

    setIsAuthorized(userAuthority === requiredAuthority);
  }, [requiredAuthority]);

  return isAuthorized ? (
    <ConnectedRoute isConnectedRoute={true}>{children}</ConnectedRoute>
  ) : (
    <PageNotFoundView />
  );
};

export default AuthorizedRoute;
