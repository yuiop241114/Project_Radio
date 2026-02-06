import React from "react";
import '../styles/mainPageCss.css'
import AxiosToken from "../api/AxiosNoToken"
import { useEffect, useState} from "react";
import { Link } from "react-router-dom";

const MainPage = () => {
  const [radioChannelList, setRadioChannelList] = useState([]);

  const getRadioChannel = async () => {
    const getData = await AxiosToken.get("/radio/list");
    setRadioChannelList(getData.data)
    console.log(getData.data);
  }

  useEffect(() => {
    getRadioChannel();
  }, []);

  if(!radioChannelList){
    return <div>채널 정보 불러오는중입니다</div>
  }
  return (
    <>
      <main className="main-container">
        {/* HERO 영역 */}
        <section className="hero">
          <h1>지금 라이브로 즐기는 커뮤니티 라디오</h1>
          <p>누구나 방송하고, 함께 듣는 실시간 라디오 플랫폼</p>

          <div className="hero-buttons">
            <Link to="/radio">채널 둘러보기</Link>
          </div>
        </section>

        {/* 추천 라디오 */}
        <section className="section">
          <h2>📻 실시간 라디오</h2>

          <div className="card-grid">
            {radioChannelList.map((channel) => (
              <div key={channel.radioChannelId} className="radio-card">
                {/* 채널 정보를 여기에 렌더링 */}
                <h3>{channel.radioChannelName}</h3>
                <div className={`status ${channel.status === true ? "live" : "ready"}`}>
                  {channel.status === true ? "ON AIR" : "방송 준비중"}
                </div>
              </div>
            ))}
          </div>
        </section>
      </main>

    </>
  );
};

export default MainPage;
