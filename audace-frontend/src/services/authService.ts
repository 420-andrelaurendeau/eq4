import { AxiosResponse } from "axios";
import http from "../constants/http";
import { Authority, DecodedJwt, LoginRequest, TimedJwt } from "../model/auth";
import {
  JWT,
  JWT_EXPIRES_AT,
  SESSION_EXPIRED_AT,
  TIME_BEFORE_EXPIRE_ISNT_RECENT,
} from "../constants/jwtConsts";
import jwtDecode from "jwt-decode";
import { AUTH_PREFIX } from "../constants/apiPrefixes";

export const login = async (
  loginRequest: LoginRequest
): Promise<AxiosResponse<TimedJwt>> => {
  return http.post(`${AUTH_PREFIX}/login`, loginRequest);
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
  clearConnection();
};

export const expireSession = () => {
  clearConnection();
  localStorage.setItem(SESSION_EXPIRED_AT, Date.now().toString());
};

const clearConnection = () => {
  localStorage.removeItem(JWT);
  localStorage.removeItem(JWT_EXPIRES_AT);
};

export const getAuthorities = (): Authority[] | null => {
  const jwt = localStorage.getItem(JWT);
  if (!jwt) return null;

  return (jwtDecode(jwt) as DecodedJwt).authorities;
};

export const getUserId = (): string | null => {
  const jwt = localStorage.getItem(JWT);
  if (!jwt) return null;

  return (jwtDecode(jwt) as DecodedJwt).id;
};

export const hasSessionExpiredRecently = (): boolean => {
  return (
    Date.now() - parseInt(localStorage.getItem(SESSION_EXPIRED_AT) || "0") <
    TIME_BEFORE_EXPIRE_ISNT_RECENT
  );
};
