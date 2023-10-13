import axios, { AxiosResponse } from "axios";
import { expireSession, getJwt, isConnected } from "../services/authService";

const http = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

http.interceptors.request.use((config) => {
  if (config?.headers && isConnected())
    config.headers.Authorization = `Bearer ${getJwt()}`;

  return config;
});

http.interceptors.response.use(
  (response: AxiosResponse<any>) => {
    if (isResponseInvalid(response)) {
      handleInvalidResponse();
    }
    return response;
  },
  (error) => {
    if (isResponseInvalid(error.response)) {
      handleInvalidResponse();
    }
    return Promise.reject(error);
  }
);

const isResponseInvalid = (response: AxiosResponse<any>) => {
  return isConnected() && (response.status === 401 || response.status === 403);
};

const handleInvalidResponse = () => {
  expireSession();
  window.location.replace("/login/disconnected");
};

export default http;
