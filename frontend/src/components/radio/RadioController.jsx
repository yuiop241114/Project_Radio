import React from "react";

const RadioController = ({
  isPlaying,
  onTogglePlay,
  volume,
  onVolumeChange,
  track,
}) => {
  return (
    <div className="radio-controls">
      <div className="track-info">
        <strong>{track.title}</strong>
        <span>{track.artist}</span>
      </div>

      <button onClick={onTogglePlay} className="play-btn">
        {isPlaying ? "⏸ Pause" : "▶ Play"}
      </button>

      <input
        type="range"
        min="0"
        max="1"
        step="0.01"
        value={volume}
        onChange={(e) => onVolumeChange(e.target.value)}
        className="volume-slider"
      />
    </div>
  );
};

export default RadioController;
