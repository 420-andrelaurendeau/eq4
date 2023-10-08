import { AxiosResponse } from "axios";
import http from "../constants/http";
import { LoginRequest, TimedJwt } from "../model/auth";

export const login = async(loginRequest: LoginRequest): Promise<AxiosResponse<TimedJwt>> => {
    return http.post("/auth/login", loginRequest);
}

export const authenticate = (timedJwt: TimedJwt) => {
    localStorage.setItem("jwt", timedJwt.jwt);
    localStorage.setItem("expiration", timedJwt.expiration.toString());
}