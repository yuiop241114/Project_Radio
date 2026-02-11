import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8081",
});

// 요청 시 자동으로 Authorization 헤더에 Token 추가
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

//응답 인터셉트(토큰 재발급)
api.interceptors.response.use(
  (response) => response,

  async (error) => {
    const originalRequest = error.config;

    // 401 + 재시도 안한 요청일 때
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        // refresh 토큰으로 재발급 요청
        const res = await axios.post(
          "http://localhost:8081/auth/refreshRT",
          {},
          { withCredentials: true } // refreshToken이 쿠키에 있다면 필수
        );

        const newAccessToken = res.data.accessToken;

        // 새 accessToken 저장
        localStorage.setItem("accessToken", newAccessToken);

        // 기존 요청에 새 토큰 넣기
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;

        // 원래 요청 다시 실행
        return api(originalRequest);

      } catch (refreshError) {
        // refresh도 실패 → 로그아웃 처리
        localStorage.removeItem("accessToken");
        window.location.href = "/login";
      }
    }

    return Promise.reject(error);
  }
);

export default api;
