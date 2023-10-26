import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";
import { EMPLOYER_PREFIX, MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";


export const getStudentOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`${STUDENT_PREFIX}/offers/${departmentId}`);
}
export const getAllOffersByEmployerId = async (employerId: number): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`${EMPLOYER_PREFIX}/offers`);
}
export const employerCreateOffer = async (offer: Offer): Promise<AxiosResponse> => {
    return http.post(`${EMPLOYER_PREFIX}/offers`, offer)
}
export const employerUpdateOffer = async (offer: Offer): Promise<AxiosResponse> => {
    return http.put(`${EMPLOYER_PREFIX}/offers`, offer)
}
export const employerDeleteOffer = async (offerId: number): Promise<AxiosResponse> => {
    return http.delete(`${EMPLOYER_PREFIX}/offers`, {
        params: {
            offerId
        }
    })
}

export const getManagerOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`${MANAGER_PREFIX}/offers/${departmentId}`);
}

export const acceptOffer = async (offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`${MANAGER_PREFIX}/accept_offer/${offerId}`);
}

export const refuseOffer = async (offerId: number): Promise<AxiosResponse<Offer>> => {
    return http.post<Offer>(`${MANAGER_PREFIX}/refuse_offer/${offerId}`);
}