import React from "react";
import '../styles/mainPageCss.css'

import Header from '../components/common/Header'
import ContentSection from '../components/common/ContentSection';

const MainPage = () => {
  return (
    <>
      <Header/>
    
      <main className="layout">
        <section className="hero-section">
          <div className="hero-text">
            <h1>Radio Cast</h1>
            <p>
              실시간 커뮤니티와 라디오 스트리밍을 결합한  
              사용자 중심 플랫폼
            </p>

            <div className="hero-actions">
              <button className="primary">커뮤니티 가기</button>
              <button className="secondary">라디오 듣기</button>
            </div>
          </div>
        </section>

        <ContentSection/>
      </main>
    </>
  );
};

export default MainPage;
