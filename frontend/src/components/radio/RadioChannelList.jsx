import React from "react";
import { useEffect, useState} from "react";
import AxiosToken from "../../api/AxiosToken";
// import { radioChannels } from "./radioChannels";

const RadioChannelList = ({ currentChannel, onSelect }) => {
  const [radioChannelList, setRadioChannelList] = useState([]);
  const channelList = async () => {
    const getData = await AxiosToken.get("/radio/list");
    const list = getData.data;
    setRadioChannelList(list);
  };
  useEffect(()=>{
    channelList();
  }, []);

  if(!radioChannelList){
    return <div className="loading">데이터를 불러오는 중입니다...</div>;
  }

  // console.log(currentChannel);
  // console.log(radioChannelList);

  return (
    <div className="channel-list">
      {radioChannelList.map((channel) => (
        <div
          key={channel.radioChannelId}
          className={`channel-item ${
            currentChannel.radioChannelId === channel.radioChannelId ? "active" : ""
          }`}
          onClick={() => onSelect(channel)}
        >
          <h3>{channel.radioChannelName}</h3>
          <p>{channel.description}</p>
        </div>
      ))}
    </div>
  );
};

export default RadioChannelList;
