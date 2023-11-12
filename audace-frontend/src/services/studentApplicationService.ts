import { AxiosResponse } from "axios";
import Application from "../model/application";
import http from "../constants/http";
import { CV } from "../model/cv";
import { STUDENT_PREFIX } from "../constants/apiPrefixes";

export const apply = async (
  application: Application
): Promise<AxiosResponse> => {
  return http.post(
    `${STUDENT_PREFIX}/${application.cv!.student!.id!}/applications`,
    application
  );
};

export const getCvsByStudentId = async (
  studentId: number
): Promise<AxiosResponse<CV[]>> => {
  return http.get<CV[]>(`${STUDENT_PREFIX}/cvs/${studentId}`);
};

export const getApplicationsByStudentId = async (
  studentId: number
): Promise<AxiosResponse<Application[]>> => {
  return http.get<Application[]>(
    `${STUDENT_PREFIX}/${studentId}/appliedOffers`
  );
};
