export interface LoginRequest {
  identification: string;
  password: string;
}

export interface TimedJwt {
  jwt: string;
  expiration: number;
}

export interface DecodedJwt {
  sub: string;
  iat: number;
  exp: number;
  id: string;
  email: string;
  authority: string;
  iss: string;
}

export enum Authority {
    STUDENT = "STUDENT",
    EMPLOYER = "EMPLOYER",
    MANAGER = "MANAGER",
}
