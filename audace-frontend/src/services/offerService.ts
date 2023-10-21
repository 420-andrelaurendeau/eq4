import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";

export const getStudentOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/${departmentId}`);
}
export const getAllOffersByEmployerId = async (employerId: number): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`/employers/offers/${employerId}`);
}
export const employerCreateOffer = async (offer: Offer): Promise<AxiosResponse> => {
    return http.post(`/employers/offers/${offer.employer.id}`)
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