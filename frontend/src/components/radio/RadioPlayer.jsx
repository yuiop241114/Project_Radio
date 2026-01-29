import React, { useRef, useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import RadioController from "./RadioController";
import { radioPlayLogic } from "./radioPlayLogic";
import AxiosToken from "../../api/AxiosToken";

const RadioPlayer = ({ currentChannel }) => {
  const audioRef = useRef(null);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [volume, setVolume] = useState(0.7);

  console.log(currentChannel)
  const playlist = currentChannel?.playlist || [];
  const currentTrack = playlist[currentIndex];

  // 볼륨 적용
  useEffect(() => {
    if (!audioRef.current) return;
    audioRef.current.volume = volume;
  }, [volume]);

  // 재생 / 일시정지
  const togglePlay = () => {
    if (!audioRef.current) return;

    if (isPlaying) {
      audioRef.current.pause();
    } else {
      audioRef.current.play();
    }
    setIsPlaying(!isPlaying);
  };

  // 곡 종료 시 다음 곡
  const handleEnded = () => {
    setCurrentIndex((prev) => (prev + 1) % playlist.length);
    setIsPlaying(true);
  };

  // 곡 변경 시 자동 재생
  useEffect(() => {
    if (!audioRef.current || !currentTrack) return;
    if (isPlaying) audioRef.current.play();
  }, [currentIndex]);

  //라디오 동기화 핵심
  // 채널 변경 시 → 새 라디오 시작
  useEffect(() => {
    if (!currentChannel || !audioRef.current) return;

    const nowTrack = async () => {
      // 기존 음악 중지
      audioRef.current.pause();

      const getData = await AxiosToken.get("/radio/now",
        { params : {radioChannelId : currentChannel.radioChannelId} }
      );

      const { trackIndex, offset } = getData.data;

      setCurrentIndex(trackIndex);
  
      setTimeout(() => {
        audioRef.current.currentTime = offset;
        audioRef.current.play();
        setIsPlaying(true);
      }, 200);
    };

    nowTrack();
  }, [currentChannel]);

  if (!currentTrack) return console.log("데모 플레이리스트 확인");

  return (
    <div className="radio-player">
      <audio
        ref={audioRef}
        src={currentTrack.src}
        onEnded={handleEnded}
      />

      <RadioController
        isPlaying={isPlaying}
        onTogglePlay={togglePlay}
        volume={volume}
        onVolumeChange={setVolume}
        track={currentTrack}
      />
    </div>
  );
};

export default RadioPlayer;
