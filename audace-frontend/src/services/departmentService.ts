import { AxiosResponse } from "axios";
import { Department } from "../model/department";
import http from "../constants/http";

export const getAllDepartments = async (): Promise<AxiosResponse<Department[]>> => {
    return http.get("/employers/departments");
}