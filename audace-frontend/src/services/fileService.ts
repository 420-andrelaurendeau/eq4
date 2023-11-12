import { AxiosResponse } from "axios";
import http from "../constants/http";
import { STUDENT_PREFIX } from "../constants/apiPrefixes";

export const uploadFile = async (
  studentId: number,
  file: File
): Promise<AxiosResponse> => {
  let formData = new FormData();
  formData.append("file", file);

  return http.post(`${STUDENT_PREFIX}/upload/${studentId}`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
