import React, { useState } from "react";
import axios from "axios";

function Login() {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const response = await axios.post("http://localhost:8081/api/login", {
        username: username,
        password: password
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
    <div>
      <h2>로그인</h2>
      <input 
        type="text" 
        placeholder="아이디"
        onChange={(e) => setUsername(e.target.value)}
      />
      <input 
        type="password" 
        placeholder="비밀번호"
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>로그인</button>
    </div>
  );
}

export default Login;
