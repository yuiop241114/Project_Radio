import { useState } from "react";

const RadioPlayer = () => {
  const [isPlaying, setIsPlaying] = useState(false);

  return (
    <div className="radio-player">
      <div className="radio-art">
        📻
      </div>

      <h2 className="radio-title">Radio Cast LIVE</h2>
      <p className="radio-desc">실시간 스트리밍 라디오</p>

      <button
        className="play-btn"
        onClick={() => setIsPlaying(!isPlaying)}
      >
        {isPlaying ? "⏸ 정지" : "▶ 재생"}
      </button>
    </div>
  );
};

export default RadioPlayer;
