import { PropsWithChildren, useEffect, useState } from "react";
import { getAuthorities } from "../../services/authService";
import ConnectedRoute from "../ConnectedRoute";
import PageNotFoundView from "../../views/PageNotFoundView";
import { Authority } from "../../model/auth";

interface Props {
  requiredAuthority: Authority;
}

const AuthorizedRoute = ({
  children,
  requiredAuthority,
}: PropsWithChildren<Props>) => {
  const [isAuthorized, setIsAuthorized] = useState<boolean>(false);

  useEffect(() => {
    let userAuthorities = getAuthorities();

    if (!userAuthorities || userAuthorities.length === 0) return;

    const requiredAuthorities = [Authority.USER, requiredAuthority];

    for (let i = 0; i < requiredAuthorities.length; i++) {
      if (!userAuthorities?.includes(requiredAuthorities[i])) {
        setIsAuthorized(false);
        return;
      }
    }

    setIsAuthorized(true);
  }, [requiredAuthority]);

  return isAuthorized ? (
    <ConnectedRoute isConnectedRoute={true}>{children}</ConnectedRoute>
  ) : (
    <PageNotFoundView />
  );
};

export default AuthorizedRoute;
