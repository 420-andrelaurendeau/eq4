import { AxiosResponse } from "axios";
import http from "../constants/http";
import { Authority, DecodedJwt, LoginRequest, TimedJwt } from "../model/auth";
import { JWT, JWT_EXPIRES_AT } from "../constants/jwtConsts";
import jwtDecode from "jwt-decode";

export const login = async (
  loginRequest: LoginRequest
): Promise<AxiosResponse<TimedJwt>> => {
  return http.post("/auth/login", loginRequest);
};

export const authenticate = (timedJwt: TimedJwt) => {
  localStorage.setItem(JWT, timedJwt.jwt);
  localStorage.setItem(
    JWT_EXPIRES_AT,
    (Date.now() + timedJwt.expiration).toString()
  );
};

export const isConnected = (): boolean => {
  const expiration = localStorage.getItem(JWT_EXPIRES_AT);
  if (!expiration) return false;

  return Date.now() < parseInt(expiration);
};

export const getJwt = (): string | null => {
  return localStorage.getItem(JWT);
};

export const logout = () => {
  localStorage.removeItem(JWT);
  localStorage.removeItem(JWT_EXPIRES_AT);
};

export const getAuthorities = (): Authority[] | null => {
  const jwt = localStorage.getItem(JWT);
  if (!jwt) return null;

  console.log(jwtDecode(jwt));

  return (jwtDecode(jwt) as DecodedJwt).authorities;
};

export const getUserId = (): string | null => {
  const jwt = localStorage.getItem(JWT);
  if (!jwt) return null;

  return (jwtDecode(jwt) as DecodedJwt).id;
}
