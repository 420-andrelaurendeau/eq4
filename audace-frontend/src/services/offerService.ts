import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";

export const addOffer = async (offer: Offer): Promise<AxiosResponse> => {
  return http.post("/offers", offer);
};

export const getOffersByDepartment = async (
  departmentId: number
): Promise<AxiosResponse<Offer[]>> => {
  return http.get<Offer[]>(`/students/offers/${departmentId}`);
};
