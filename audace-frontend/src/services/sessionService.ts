import { AxiosResponse } from "axios";
import http from "../constants/http";
import { Session } from "../model/session";

export const getAllSessions = async (): Promise<AxiosResponse<Session[]>> => {
    return http.get<Session[]>(`/users/sessions`);
}