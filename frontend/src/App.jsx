import { useState, useEffect } from 'react'
import axios from 'axios'
import './App.css'

import Login from './api/Login'
import SignUp from './pages/SignUp'

function App() {
  /*
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
    */

  const [message, setMessage] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8081/api/test")
      .then(res => {
        setMessage(res.data);
      })
      .catch(err => {
        console.error("API 호출 실패:", err);
      });
  }, []);

  return (
    <>
      <div style={{ padding: "20px" }}>
        <h1>React → Spring Boot API 테스트</h1>
        <p>결과: {message}</p>
        <Login/>
        <SignUp/>
      </div>
    </>
  );
}

export default App
