import "../../styles/RadioChannelManage.css";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import AxiosToken from "../../api/AxiosToken";

const radioChannelManage = () => {
  const [radioChannelId, setRadioChannelId] = useState(null);
  const id = localStorage.getItem("id");
  const [channel, setChannel] = useState(null);
  // console.log(localStorage.getItem("id"))

  const checkChannel = async () => {
    const getData = await AxiosToken.get('/radio/detail/user', {
      params : {radioUserId : id}
    })
    setChannel(getData.data)
    setRadioChannelId(getData.data.radioChannelId)
    // console.log(getData.data);
  };

  // const fetchChannel = async () => {
  //   const res = await AxiosToken.get('/radio/detail',{
  //     params : {radioChannelId : radioChannelId}
  //   });
  //   setChannel(res.data);
  //   // console.log(res.data)
  // };
  
  useEffect(() => {
    checkChannel();
  }, [radioChannelId]);

  const startBroadcast = async () => {
    await AxiosToken.post(`/radio/channel/start/${radioChannelId}/${id}`);
    checkChannel();
    alert("방송 시작");
  };

  const stopBroadcast = async () => {
    await AxiosToken.post(`/radio/channel/stop/${radioChannelId}/${id}`);
    checkChannel();
    alert("방송 종료");
  };

  const deleteBroadcast = () =>{
    const result = confirm("정말 삭제 하시겠습니까?");
    console.log(result);
  };

  if (!channel) return <h2 className="my-channel-manage">생성한 채널이 존재하지 않습니다</h2>;

  return (
    <div className="my-channel-manage">
      <h2>🎙 {channel.radioChannelName}</h2>

      <div className="channel-status-card">
        <p className={channel.status ? "on" : "off"}>
          {channel.status ? "● 방송중" : "● 방송 준비중"}
        </p>

        <p className="desc">{channel.description}</p>

        <div className="actions">
          <button className="del" onClick={deleteBroadcast}>채널 삭제</button>

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
