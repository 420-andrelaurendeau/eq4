import { AxiosResponse } from "axios";
import http from "../constants/http";
import { Session } from "../model/session";

export const getAllSessions = async (): Promise<AxiosResponse<Session[]>> => {
  return http.get<Session[]>(`/users/sessions`);
};

export const getCurrentSession = async (): Promise<AxiosResponse<Session>> => {
  return http.get<Session>(`/users/sessions/current`);
};

export const getNextSession = async (): Promise<AxiosResponse<Session>> => {
  return http.get<Session>(`/users/sessions/next`);
};

export const getSessionById = async (
  sessionId: number
): Promise<AxiosResponse<Session>> => {
  return http.get<Session>(`/users/sessions/${sessionId}`);
};
