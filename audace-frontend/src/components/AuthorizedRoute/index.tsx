import { PropsWithChildren, useEffect, useState } from "react";
import { getAuthority } from "../../services/authService";
import ConnectedRoute from "../ConnectedRoute";

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
    <></>
  );
};

export default AuthorizedRoute;
