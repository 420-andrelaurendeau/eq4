import axios from "axios";
import { getJwt, isConnected } from "../services/authService";

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

export default http;
