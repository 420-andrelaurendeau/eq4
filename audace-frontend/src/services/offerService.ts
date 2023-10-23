import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";
import { EMPLOYER_PREFIX, MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";

export const getStudentOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`${STUDENT_PREFIX}/offers/${departmentId}`);
}
export const getAllOffersByEmployerId = async (employerId: number): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`${EMPLOYER_PREFIX}/${employerId}/offers`);
}
export const employerCreateOffer = async (offer: Offer): Promise<AxiosResponse> => {
    return http.post(`${EMPLOYER_PREFIX}/${offer.employer.id}/offers`)
}

export const getManagerOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/managers/offers/${departmentId}`);
}

//OfferButton
export const acceptOffer = async (managerId : number, offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`${MANAGER_PREFIX}/${managerId}/accept_offer/${offerId}`);
}

export const refuseOffer = async (managerId : number, offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`${MANAGER_PREFIX}/${managerId}/refuse_offer/${offerId}`);
}