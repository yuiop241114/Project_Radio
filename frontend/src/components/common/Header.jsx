import React from "react";
import "../../styles/header.css";
import { useNavigate } from "react-router-dom";

import '../../styles/header.css'

const Header = () => {
  const username = localStorage.getItem("username");
  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  return (
    <header className="header">
      <div className="header-left">Radio Cast</div>

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
