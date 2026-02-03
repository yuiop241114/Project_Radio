import "../../styles/RadioChannelManage.css";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AxiosToken from "../../api/AxiosToken";

const radioChannelManage = () => {
  const { radioChannelId } = useParams();
  const id = localStorage.getItem("id");
  const [channel, setChannel] = useState(null);
  // console.log(localStorage.getItem("id"))

  const fetchChannel = async () => {
    const res = await AxiosToken.get('/radio/detail',{
      params : {radioChannelId : radioChannelId}
    });
    setChannel(res.data);
    // console.log(res.data)
  };
  
  useEffect(() => {
    fetchChannel();
  }, [radioChannelId]);

  const startBroadcast = async () => {
    await AxiosToken.post(`/radio/channel/start/${radioChannelId}/${id}`);
    fetchChannel();
    alert("방송 시작");
  };

  const stopBroadcast = async () => {
    await AxiosToken.post(`/radio/channel/stop/${radioChannelId}/${id}`);
    fetchChannel();
    alert("방송 종료");
  };

  if (!channel) return null;

  return (
    <div className="my-channel-manage">
      <h2>🎙 {channel.radioChannelName}</h2>

      <div className="channel-status-card">
        <p className={channel.status ? "on" : "off"}>
          {channel.status ? "● 방송중" : "● 방송 준비중"}
        </p>

        <p className="desc">{channel.description}</p>

        <div className="actions">
          {channel.status ? (
            <button className="stop" onClick={stopBroadcast}>
              방송 종료
            </button>
          ) : (
            <button className="start" onClick={startBroadcast}>
              방송 시작
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default radioChannelManage;
