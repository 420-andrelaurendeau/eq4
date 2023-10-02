import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";
import {Employer} from "../model/user";

export const getOffersByDepartment = async (departmentId: number): Promise<AxiosResponse<Offer[]>> => {
    return http.get<Offer[]>(`/students/offers/${departmentId}`);
}
export const getAllOffersByEmployerId = async (employerId: any): Promise<AxiosResponse<Offer[]>> =>{
    return http.get(`/employers/${employerId}/offers`);
}
