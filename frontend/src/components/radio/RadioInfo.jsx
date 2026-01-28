import React from "react";
import "../../styles/RadioInfo.css";
import { useParams } from "react-router-dom"; 
import { useEffect, useState} from "react";
import AxiosToken from "../../api/AxiosToken";

const RadioInfo = ({ channel }) => {
  // const [info, setInfo] = useState(null);
  // const channelInfo = async () => {
  //   const getData = await AxiosToken.get("/radio/detail",{
  //     params : {radioChannelId : channel.radioChannelId}
  //   });
  //   setInfo(getData.data);
  //   // console.log(getData.data)
  // }
  // useEffect(() =>{channelInfo()}, [channel.radioChannelId]);
  // if (!info) {
  //   return <div className="loading">데이터를 불러오는 중입니다...</div>;
  // }
  
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
