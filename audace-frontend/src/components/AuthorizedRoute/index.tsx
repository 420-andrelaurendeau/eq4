import { PropsWithChildren, useEffect, useState } from "react";
import { getAuthority, isConnected } from "../../services/authService";

interface Props {
  requiredAuthority: string;
}

const AuthorizedRoute = ({ children, requiredAuthority }: PropsWithChildren<Props>) => {
  const [isAuthorized, setIsAuthorized] = useState<boolean>(false);

  useEffect(() => {
    let userAuthority = getAuthority();

    if (!userAuthority) return;

    setIsAuthorized(userAuthority === requiredAuthority && isConnected());
  }, [requiredAuthority]);

  return isAuthorized ? <>{children}</> : <></>;
};

export default AuthorizedRoute;
