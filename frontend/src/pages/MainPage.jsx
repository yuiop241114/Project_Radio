import React from "react";
import '../styles/MainPageCss.css'

const MainPage = () => {
  return (
    <>
      <main className="main-container">
        <section className="hero">
          <h1>Radio Cast</h1>
          <p>실시간 커뮤니티와 라디오 스트리밍을 결합한 서비스</p>
        </section>

        <section className="features">
          <div className="feature-card">
            <h3>실시간 커뮤니티</h3>
            <p>JWT 인증 기반 사용자 커뮤니티</p>
          </div>

          <div className="feature-card">
            <h3>라디오 기능</h3>
            <p>주파수 기반 스트리밍 (구현 예정)</p>
          </div>
        </section>

        <section className="api-test">
          <button>인증 API 테스트</button>
        </section>
      </main>

      <footer className="footer">
        <p>© 2025 Radio Cast Project</p>
      </footer>
    </>
  );
};

export default MainPage;
