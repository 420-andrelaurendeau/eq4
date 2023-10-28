import { AxiosResponse } from "axios";
import http from "../constants/http";
import Application from "../model/application";
import { getUserId } from "./authService";

export const getAllApplicationsByOfferId = async (id: number): Promise<AxiosResponse<Application[]>> => {
    return http.get<Application[]>(`/employers/${getUserId()}/offers/${id}/applications`);
}