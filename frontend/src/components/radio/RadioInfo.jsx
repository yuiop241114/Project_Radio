import React from "react";
import "../../styles/RadioInfo.css";

const RadioInfo = ({ channel }) => {
  console.log(channel);
  if (!channel) {
    return <div className="radio-info">채널을 선택해주세요</div>;
  }

  return (
    <div className="radio-info">
      <span className={`status ${channel.isLive ? "live" : "ready"}`}>
        {channel.isLive ? "ON AIR" : "방송 준비중"}
      </span>

      <h2 className="radio-title">{channel.title}</h2>
      <p className="radio-desc">{channel.description}</p>
    </div>
  );
};

export default RadioInfo;
