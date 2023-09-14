import { AxiosResponse } from "axios";
import { Employer } from "../model/user";
import http from "../constants/http";

export const employerSignup = async (employer: Employer): Promise<AxiosResponse> => {
    return http.post('/employer/signup', employer);
}