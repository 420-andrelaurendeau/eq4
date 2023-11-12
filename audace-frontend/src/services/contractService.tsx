import { AxiosResponse } from "axios";
import { MANAGER_PREFIX } from "../constants/apiPrefixes";
import http from "../constants/http";
import { Contract } from "../model/contract";

export const createContract = async (
  contract: Contract
): Promise<AxiosResponse> => {
  return http.post(`${MANAGER_PREFIX}/contracts`, contract);
};

export const getContractById = async (id: number): Promise<AxiosResponse> => {
  return http.get(`${MANAGER_PREFIX}/contracts/${id}`);
};
export const getContractByApplicationId = async (
  applicationId: number
): Promise<AxiosResponse<Contract>> => {
  return http.get<Contract>(
    `${MANAGER_PREFIX}/applications/${applicationId}/contract`
  );
};

export const getContractsByDepartmentId = async (
  departmentId: number
): Promise<AxiosResponse<Contract[]>> => {
  return http.get<Contract[]>(
    `${MANAGER_PREFIX}/contracts/department/${departmentId}`
  );
};
