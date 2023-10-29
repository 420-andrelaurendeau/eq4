import { AxiosResponse } from "axios";
import { Contract } from "../model/contract";
import http from "../constants/http";

export const managerCreateContract = async (contract: Contract): Promise<AxiosResponse> => {
    return http.post('/managers/contracts/', contract);
}

export const managerGetContractById = async (id: number): Promise<AxiosResponse> => {
    return http.get(`/managers/contracts/${id}`);
}

