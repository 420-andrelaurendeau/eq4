import { AxiosResponse } from "axios";
import { Employer } from "../model/user";
import http from "../constants/http";

export const getEmployerById = async (id: number): Promise<AxiosResponse<Employer>> => {
    return http.get<Employer>(`/employers/${id}`);
};