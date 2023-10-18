import { AxiosResponse } from "axios";
import http from "../constants/http";
import Application from "../model/application";
import { Offer } from "../model/offer";

export const getAllApplicationsByOfferId = async (id: number): Promise<AxiosResponse<Map<Offer, Application[]>>> => {
    return http.get<Map<Offer, Application[]>>(`/employers/offers/applications/${id}`);
}