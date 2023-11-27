import { AxiosResponse } from "axios";
import { EMPLOYER_PREFIX, MANAGER_PREFIX, STUDENT_PREFIX } from "../constants/apiPrefixes";
import http from "../constants/http";
import { Contract, Signature } from "../model/contract";
import { Authority } from "../model/auth";

export const createContract = async (
  contract: Contract
): Promise<AxiosResponse> => {
  return http.post(`${MANAGER_PREFIX}/contracts`, contract);
};

export const getContractById = async (id: number, userType: Authority): Promise<AxiosResponse> => {
  const PREFIX = getPrefix(userType);
  return http.get(`${PREFIX}/contracts/${id}`);
};

export const getContractByApplicationId = async (applicationId: number, userType: Authority): Promise<AxiosResponse<Contract>> => {
  const PREFIX = getPrefix(userType);
  return http.get<Contract>(
    `${PREFIX}/applications/${applicationId}/contract`
  );
};

export const getContractsByDepartmentId = async (departmentId: number): Promise<AxiosResponse<Contract[]>> => {
  return http.get<Contract[]>(
    `${MANAGER_PREFIX}/contracts/department/${departmentId}`
  );
};

export const signContract = async (contractId: number, userId: number, userType: Authority): Promise<AxiosResponse> => {
  return http.post<Contract>(
    userType === Authority.MANAGER ?
      `${MANAGER_PREFIX}/${userId}/sign_contract/${contractId}` :
      `${getPrefix(userType)}/sign_contract/${contractId}`,
  );
}

export const getSignaturesByContractId = async (contractId: number, userType: Authority): Promise<AxiosResponse<Signature[]>> => {
  const PREFIX = getPrefix(userType);
  return http.get<Signature[]>(
    `${PREFIX}/contracts/${contractId}/signatures`,
  );
}

const getPrefix = (userType: Authority) => {
  switch (userType) {
    case Authority.MANAGER:
      return MANAGER_PREFIX;
    case Authority.STUDENT:
      return STUDENT_PREFIX;
    case Authority.EMPLOYER:
      return EMPLOYER_PREFIX;
  }
};
