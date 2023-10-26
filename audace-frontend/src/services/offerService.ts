import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";
import { EMPLOYER_PREFIX, MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";
import Application from "../model/application";
import {CV} from "../model/cv";
import {Student} from "../model/user";
import {getStudentById} from "./userService";

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

export const getAcceptedApplicationsByDepartment = async (departmentId: number): Promise<AxiosResponse<Application[]>> => {
    return http.get<Application[]>(`${MANAGER_PREFIX}/acceptedApplications/${departmentId}`);
}

export const getStudentByCv = async (cv: CV): Promise<AxiosResponse<Student>> => {
    return http.get<Student>(`${STUDENT_PREFIX}/${cv.student.id}`);
}

export const getStudentByApplication = async (application: Application): Promise<AxiosResponse<Student>> => {
    const studentCv: CV = application.cv!;
    const applicationStudent = await getStudentByCv(studentCv);
    return http.get<Student>(`${STUDENT_PREFIX}/${applicationStudent.data.id}`);
}