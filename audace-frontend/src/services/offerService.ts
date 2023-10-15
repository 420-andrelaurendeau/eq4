import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";
import Application from "../model/application";

export const getStudentOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/${departmentId}`);
}
export const getAllOffersByEmployerId = async (employerId: number): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`/employers/${employerId}/offers`);
}
export const employerCreateOffer = async (offer: Offer): Promise<AxiosResponse> => {
    return http.post(`/employers/${offer.employer.id}/offers`)
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