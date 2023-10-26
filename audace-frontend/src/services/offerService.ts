import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";

export const getStudentOffersByDepartment = async (departmentId: number, sessionId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/${departmentId}/${sessionId}`);
}
export const getAllOffersByEmployerId = async (employerId: number, sessionId: number): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`/employers/${employerId}/offers/${sessionId}`);
}
export const employerCreateOffer = async (offer: Offer): Promise<AxiosResponse> => {
    return http.post(`/employers/${offer.employer.id}/offers`)
}

export const getManagerOffersByDepartment = async (departmentId: number, sessionId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/managers/offers/${departmentId}/${sessionId}`);
}

//OfferButton
export const acceptOffer = async (managerId : number, offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`/managers/${managerId}/accept_offer/${offerId}`);
}

export const refuseOffer = async (managerId : number, offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`/managers/${managerId}/refuse_offer/${offerId}`);
}