import { useState, useEffect} from "react";
import RadioInfo from "../components/radio/RadioInfo";
import RadioChannelList from "../components/radio/RadioChannelList";
import RadioPlayer from "../components/radio/RadioPlayer";
// import { radioChannels } from "../components/radio/radioChannels";
import AxiosToken from "../api/AxiosToken";
import "../styles/radioPage.css";

const RadioPage = () => {
  const [radioChannelList, setRadioChannelList] = useState([]);
  const [currentChannel, setCurrentChannel] = useState(null);

  const getRadioList = async () => {
    try {
      const response = await AxiosToken.get("/radio/list");
      const data = response.data; // 서버에서 온 데이터를a 변수에 담음
      setRadioChannelList(data); // 리스트 업데이트
      // 상태 변수(radioChannelList)가 아닌, 방금 받은 데이터(data)를 직접 활용!
      if (data && data.length > 0) {
        setCurrentChannel(data[0]); 
      }
    } catch (error) {
      console.error("데이터 로드 실패:", error);
    }
  };
  useEffect(() => {
    getRadioList();
  },[]);
  // console.log(radioChannelList);

  // 중요: 데이터가 로딩 중일 때(currentChannel이 null일 때) 렌더링 방어
  if (!currentChannel) {
    return <div className="loading">데이터를 불러오는 중입니다...</div>;
  }

  return (
    <div className="radio-layout">
      <RadioChannelList
        currentChannel={currentChannel}
        onSelect={setCurrentChannel}
      />

      <div className="radio-main">
        <RadioInfo channel={currentChannel} />
        <RadioPlayer currentChannel={currentChannel} />
      </div>
    </div>
  );
};

export default RadioPage;
