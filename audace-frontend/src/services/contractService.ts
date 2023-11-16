import { AxiosError, AxiosResponse } from "axios";
import { EMPLOYER_PREFIX, MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";
import http from "../constants/http";
import { Contract, Signature } from "../model/contract";

export const createContract = async (
  contract: Contract
): Promise<AxiosResponse> => {
  return http.post(`${MANAGER_PREFIX}/contracts`, contract);
};

export const getContractById = async (id: number, user: string): Promise<AxiosResponse> => {
  const PREFIX = getPrefix(user);
  return http.get(`${PREFIX}/contracts/${id}`);
};

export const getContractByApplicationId = async (applicationId: number, user: string): Promise<AxiosResponse<Contract>> => {
  const PREFIX = getPrefix(user);
  return http.get<Contract>(
    `${PREFIX}/applications/${applicationId}/contract`
  );
};

export const getContractsByDepartmentId = async (departmentId: number): Promise<AxiosResponse<Contract[]>> => {
  return http.get<Contract[]>(
    `${MANAGER_PREFIX}/contracts/department/${departmentId}`
  );
};

export const signContractByManager = async (managerId: number, contractId: number): Promise<AxiosResponse<Contract>> => {
  return http.post<Contract>(
    `${MANAGER_PREFIX}/${managerId}/sign_contract/${contractId}`,
  );
}

export const signContract = async (contractId: number, user: string): Promise<AxiosResponse> => {
  const PREFIX = getPrefix(user);
  try {
    return await http.post(
      `${PREFIX}/sign_contract`, null, {
      params: { contractId }
    }
    );
  } catch (error) {
    const axiosError = error as AxiosError;
    console.error('Error in signContract:', axiosError.message);
    if (axiosError.response) {
      console.error('Server responded with:', axiosError.response);
    }
    throw axiosError;
  }
};

export const getSignaturesByContractId = async (contractId: number, user: string): Promise<AxiosResponse<Signature[]>> => {
  const PREFIX = getPrefix(user);
  return http.get<Signature[]>(
    `${PREFIX}/contracts/${contractId}/signatures`,
  );
}

const getPrefix = (user: string): string | null => {
  switch (user) {
    case 'manager':
      return MANAGER_PREFIX;
    case 'student':
      return STUDENT_PREFIX;
    case 'employer':
      return EMPLOYER_PREFIX;
    default:
      return null;
  }
};
