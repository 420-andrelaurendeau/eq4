import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";

export const getStudentOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/${departmentId}`);
}

export const getManagerOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/managers/offers/${departmentId}`);
}