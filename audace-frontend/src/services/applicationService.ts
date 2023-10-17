import { AxiosResponse } from "axios";
import http from "../constants/http";
import Application from "../model/application";

export const getAllApplicationsByOfferId = async (id: number): Promise<AxiosResponse<Application>> => {
    return http.get<Application>(`/offers/applications/${id}`);
}