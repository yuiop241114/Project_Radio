import React from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import axiosToken from "../../api/AxiosNoToken";

import "../../styles/header.css";
import '../../styles/header.css'

const Header = () => {
  const username = localStorage.getItem("username");
  const navigate = useNavigate();

  const logout = () => {
    axiosToken.post('/auth/logout', {},
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("refreshToken")}`
        }
      }
    );
    localStorage.clear();
    window.location.href = "/";
  };

  return (
    <header className="header">
      <div className="header-left">
        <Link to="/" className="logo">🎧 Radio Cast</Link>
      </div>

      <nav className="header-nav">
        <a href="/">홈</a>
        <a href="/community">커뮤니티</a>
        <a href="/radio">라디오</a>
      </nav>

      <div className="header-right">
        {username ? (
          <>
            <span className="user">{username}</span>
            <button onClick={logout}>로그아웃</button>
          </>
        ) : (
          <button onClick={() => navigate('/login')}>로그인</button>
        )}
      </div>
    </header>
  );
};

export default Header;
