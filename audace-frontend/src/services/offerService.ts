import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";
import {Employer} from "../model/user";

export const getStudentOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/${departmentId}`);
}
export const getAllOffersByEmployerId = async (employerId: any): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`/employers/${employerId}/offers`);
}

export const getManagerOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/managers/offers/${departmentId}`);
}

//OfferButton
export const acceptOffer = async (offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`/managers/accept_offer/${offerId}`);
}

export const refuseOffer = async (offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`/managers/refuse_offer/${offerId}`);
}