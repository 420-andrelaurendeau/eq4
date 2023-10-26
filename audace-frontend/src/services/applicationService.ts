import { AxiosResponse } from "axios";
import http from "../constants/http";
import Application from "../model/application";
import {EMPLOYER_PREFIX, STUDENT_PREFIX} from "../constants/apiPrefixes";

export const getAllApplicationsByEmployerId = async (employerId: number): Promise<AxiosResponse<Map<number, Application[]>>> => {
    return http.get<Map<number, Application[]>>(`${EMPLOYER_PREFIX}/offers/applications`, {
        params: {
            employerId
        }
    });
}

export const studentApplyToOffer = async (application: Application): Promise<AxiosResponse> => {
    return http.post(`${STUDENT_PREFIX}/applications`, application);
}

export const employerAcceptApplication = async (employerId: number, applicationId: number): Promise<AxiosResponse<Application>> => {
    return http.put(`${EMPLOYER_PREFIX}/accept_application/${applicationId}`, {
        params: {
            employerId
        }
    });
}
export const employerRefuseApplication = async (employerId: number, applicationId: number): Promise<AxiosResponse<Application>> => {
    return http.put(`${EMPLOYER_PREFIX}/refuse_application/${applicationId}`, {
        params: {
            employerId
        }
    });
}

export const getApplicationsByStudentId = async (studentId: number): Promise<AxiosResponse<Application[]>> => {
    return http.get<Application[]>(`${STUDENT_PREFIX}/appliedOffers`, {
        params: {
            studentId
        }
    });
}