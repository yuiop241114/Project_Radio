import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import '../../styles/login.css';

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    //백엔드로 요청이 넘어가기 전에 새로고침을 막이 위한 코드(백엔드로 정보가 넘어가기 전에 새로고침이 되는 경우 정보가 안넘어감)
    e.preventDefault(); 
    
    //console.log("보낼 데이터:", { username, password });
    
    try {
      const response = await axios.post("http://localhost:8081/auth/login", {
        email: email,
        password: password,
      });

      // 서버에서 받은 JWT 토큰
      //const token = response.data.token;

      // 토큰 저장 (LocalStorage)
      localStorage.setItem("accessToken", response.data.accessToken);
      localStorage.setItem("refreshToken", response.data.refreshToken);
      localStorage.setItem("username", response.data.username);

      //메인페이지로 이동
      navigate("/");

    } catch (err) {
      alert("로그인 실패");
      console.error(err);
    }
  };

  return (
    <>
    <div className="login-container">
      <div className="login-box">
        <Link to="/" className="login-logo">🎧 Radio Cast</Link>

        <form className="login-form" onSubmit={handleLogin}>
          <input type="text" name="email" placeholder="아이디" onChange={(e) => setEmail(e.target.value)}/>
          <input type="password" name="password" placeholder="비밀번호" onChange={(e) => setPassword(e.target.value)}/>
          <button type="submit">로그인</button>
        </form>

        <div className="login-footer">
          <span>계정이 없으신가요?</span>
          <Link to="/signup">회원가입</Link>
        </div>
      </div>
    </div>
    </>
  );
}

export default Login;
