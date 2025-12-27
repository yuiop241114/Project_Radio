import React from "react";
import '../styles/mainPageCss.css'

const MainPage = () => {
  return (
    <>
      <main className="main-container">
        {/* HERO 영역 */}
        <section className="hero">
          <h1>지금 라이브로 즐기는 커뮤니티 라디오</h1>
          <p>누구나 방송하고, 함께 듣는 실시간 라디오 플랫폼</p>

          <div className="hero-buttons">
            <button className="btn-primary">실시간 방송 듣기</button>
            <button className="btn-outline">채널 둘러보기</button>
          </div>
        </section>

        {/* 추천 라디오 */}
        <section className="section">
          <h2>🔥 인기 라디오</h2>

          <div className="card-grid">
            {[1, 2, 3, 4].map((item) => (
              <div className="radio-card" key={item}>
                <div className="thumbnail" />
                <h3>밤의 재즈 라디오</h3>
                <p>청취자 124명</p>
              </div>
            ))}
          </div>
        </section>
      </main>

      {/* <ContentSection/> */}
    </>
  );
};

export default MainPage;
