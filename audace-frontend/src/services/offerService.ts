import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";

export const getStudentOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/${departmentId}`);
}
export const getAllOffersByEmployerId = async (employerId: number): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`/employers/${employerId}/offers`);
}
export const employerCreateOffer = async (offer: Offer): Promise<AxiosResponse> => {
    return http.post(`/employers/${offer.employer.id}/offers`, offer);
}

export const getManagerOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/managers/offers/${departmentId}`);
}

//OfferButton
export const acceptOffer = async (managerId : number, offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`/managers/${managerId}/accept_offer/${offerId}`);
}


export const getEmployersOfferById = async (offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.get<Offer>(`/employers/offers/${offerId}`);
}
export const refuseOffer = async (managerId : number, offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`/managers/${managerId}/refuse_offer/${offerId}`);
}