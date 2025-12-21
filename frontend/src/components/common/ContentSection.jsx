import React from "react";

const FeatureSection = () => {
  return (
    <section className="feature-section">
      <div className="feature">
        <h3>실시간 커뮤니티</h3>
        <p>
          JWT 인증 기반으로 사용자 간  
          실시간 소통이 가능한 커뮤니티
        </p>
      </div>

      <div className="feature">
        <h3>라디오 스트리밍</h3>
        <p>
          주파수 기반 라디오 서비스  
          (Streaming / WebSocket 연계 예정)
        </p>
      </div>
    </section>
  );
};

export default FeatureSection;
