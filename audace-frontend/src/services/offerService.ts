import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";

export const getOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/department/${departmentId}`);
}