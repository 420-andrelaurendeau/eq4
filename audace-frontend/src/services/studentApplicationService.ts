import { AxiosResponse } from "axios";
import Application from "../model/application";
import http from "../constants/http";

export const apply = async (application: Application): Promise<AxiosResponse> => {
    return http.post(`/students/${application.studentId}/applications`, application);
}

