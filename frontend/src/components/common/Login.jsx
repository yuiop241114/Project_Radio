import React, { useState } from "react";
import axios from "axios";


function Login() {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async (e) => {
    //백엔드로 요청이 넘어가기 전에 새로고침을 막이 위한 코드(백엔드로 정보가 넘어가기 전에 새로고침이 되는 경우 정보가 안넘어감)
    e.preventDefault(); 
    
    console.log("보낼 데이터:", { username, password });
    
    try {
      const response = await axios.post("http://localhost:8081/auth/login", {
        username: username,
        password: password,
      });

      // 서버에서 받은 JWT 토큰
      const token = response.data.token;

      // 토큰 저장 (LocalStorage)
      localStorage.setItem("token", token);

      alert("로그인 성공!");
    } catch (err) {
      alert("로그인 실패");
      console.error(err);
    }
  };

  return (
    <>
      <h2>로그인</h2>
      <form onSubmit={handleLogin}>
        <input type="text" name="username" placeholder="아이디" onChange={(e) => setUsername(e.target.value)}/>
        <input type="password" name="password" placeholder="비밀번호" onChange={(e) => setPassword(e.target.value)}/>
        <button type="submit">로그인</button>
      </form>
    </>
  );
}

export default Login;
