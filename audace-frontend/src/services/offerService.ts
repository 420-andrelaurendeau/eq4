import { AxiosResponse } from "axios";
import { Offer } from "../model/offer";
import http from "../constants/http";
import {
  EMPLOYER_PREFIX,
  MANAGER_PREFIX,
  STUDENT_PREFIX,
} from "../constants/apiPrefixes";

export const getStudentOffersByDepartment = async (departmentId: number, sessionId: number): Promise<AxiosResponse<Offer[]>> => {
  return http.get<Offer[]>(
    `${STUDENT_PREFIX}/offers/${departmentId}/${sessionId}`
  );
};

export const employerCreateOffer = async (offer: Offer): Promise<AxiosResponse> => {
  return http.post(`${EMPLOYER_PREFIX}/offers`, offer);
};

export const employerUpdateOffer = async (offer: Offer): Promise<AxiosResponse> => {
  return http.put(`${EMPLOYER_PREFIX}/offers`, offer);
};

export const employerDeleteOffer = async (offerId: number): Promise<AxiosResponse> => {
  return http.delete(`${EMPLOYER_PREFIX}/offers`, {
    params: {
      offerId,
    },
  });
};

export const getAllOffersByEmployerIdAndSessionId = async (employerId: number, sessionId: number): Promise<AxiosResponse<Offer[]>> => {
  return http.get(`${EMPLOYER_PREFIX}/offers/${sessionId}`, {
    params: {
      employerId,
    },
  });
};

//OfferButton
export const acceptOffer = async (managerId: number, offerId: number): Promise<AxiosResponse<Offer>> => {
  return http.post<Offer>(
    `${MANAGER_PREFIX}/${managerId}/accept_offer/${offerId}`
  );
};

export const refuseOffer = async (managerId: number, offerId: number): Promise<AxiosResponse<Offer>> => {
  return http.post<Offer>(
    `${MANAGER_PREFIX}/${managerId}/refuse_offer/${offerId}`
  );
};

export const getManagerOffersByDepartment = async (departmentId: number, sessionId: number): Promise<AxiosResponse<Offer[]>> => {
  return http.get<Offer[]>(
    `${MANAGER_PREFIX}/offers/${departmentId}/${sessionId}`
  );
};

export const getEmployersOfferById = async (offerId: number): Promise<AxiosResponse<Offer>> => {
  return http.get<Offer>(`${EMPLOYER_PREFIX}/offers`, {
    params: {
      offerId,
    },
  });
};
