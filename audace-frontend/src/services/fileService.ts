import { AxiosResponse } from "axios";
import http from "../constants/http";

export const uploadFile = async (studentId: number, file: File): Promise<AxiosResponse> => {
    let formData = new FormData();
    formData.append('file', file);

    return http.post(`/students/upload/${studentId}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}