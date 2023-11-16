import { AxiosError, AxiosResponse } from "axios";
import { EMPLOYER_PREFIX, MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";
import http from "../constants/http";
import { Contract } from "../model/contract";

export const createContract = async (
  contract: Contract
): Promise<AxiosResponse> => {
  return http.post(`${MANAGER_PREFIX}/contracts`, contract);
};

export const getContractById = async (id: number, user: string): Promise<AxiosResponse> => {
  const prefix = user === 'manager' ? MANAGER_PREFIX : user === 'student' ? STUDENT_PREFIX : user === 'employer' ? EMPLOYER_PREFIX : null;
  return http.get(`${prefix}/contracts/${id}`);
};

export const getContractByApplicationId = async (
  applicationId: number,
  user: string
): Promise<AxiosResponse<Contract>> => {
  const prefix = user === "student" ? STUDENT_PREFIX : MANAGER_PREFIX;
  return http.get<Contract>(
    `${prefix}/applications/${applicationId}/contract`
  );
};

export const getContractsByDepartmentId = async (
  departmentId: number
): Promise<AxiosResponse<Contract[]>> => {
  return http.get<Contract[]>(
    `${MANAGER_PREFIX}/contracts/department/${departmentId}`
  );
};

export const signContractByStudent = async (contractId: number): Promise<AxiosResponse> => {
  try {
    return await http.post(
      `${STUDENT_PREFIX}/sign_contract`, null, {
      params: { contractId }
    }
    );
  } catch (error) {
    const axiosError = error as AxiosError;
    console.error('Error in signContractByStudent:', axiosError.message);
    if (axiosError.response) {
      console.error('Server responded with:', axiosError.response);
    }
    throw axiosError;
  }

};

export const ManagerSignContract = async (
  managerId: number,
  contractId: number
): Promise<AxiosResponse<Contract>> => {
  return http.post<Contract>(
    `${MANAGER_PREFIX}/${managerId}/sign_contract/${contractId}`,
  );
}


