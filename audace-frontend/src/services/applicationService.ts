import { AxiosResponse } from "axios";
import http from "../constants/http";
import Application from "../model/application";

export const getAllApplicationsByEmployerId = async (id: number): Promise<AxiosResponse<Map<number, Application[]>>> => {
    return http.get<Map<number, Application[]>>(`/employers/${id}/offers/applications`);
}