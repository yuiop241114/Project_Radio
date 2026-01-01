import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8081",
});

// 요청 시 자동으로 Authorization 헤더에 Token 추가
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
