import React, { useRef, useState, useEffect } from "react";
import RadioController from "./RadioController";
import AxiosToken from "../../api/AxiosToken";

const RadioPlayer = ({ currentChannel }) => {
  const audioRef = useRef(null);
  const [playlist, setPlaylist] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [volume, setVolume] = useState(0.7);
  const [loading, setLoading] = useState(true);
  const [initialOffset, setInitialOffset] = useState(0); // 누락되었던 상태 추가

  // 현재 트랙 계산 (안전하게 처리)
  const currentTrack = playlist.length > 0 ? playlist[currentIndex] : null;

  const initializeRadio = async () => {
    if (!currentChannel?.radioChannelId) return;

    try {
      setLoading(true);

      const [playlistRes, nowRes] = await Promise.all([
        AxiosToken.get("/radio/playlist", { params: { playlistId: currentChannel.playlistId } }),
        AxiosToken.get("/radio/now", { params: { radioChannelId: currentChannel.radioChannelId } })
      ]);

      const newPlaylist = playlistRes.data;
      const nowData = nowRes.data; // RadioTrackResponse 객체

      setPlaylist(newPlaylist);

      // 🔥 핵심: 백엔드에서 받은 radioTrackId가 playlist의 몇 번째 인덱스인지 찾습니다.
      const foundIndex = newPlaylist.findIndex(
        (track) => track.radioTrackId === nowData.radioTrackId
      );

      // 찾지 못했다면 0번 인덱스 사용
      setCurrentIndex(foundIndex !== -1 ? foundIndex : 0);
      setInitialOffset(nowData.offset || 0);

    } catch (error) {
      console.error("❌ 데이터 로딩 실패:", error);
    } finally {
      setTimeout(() => setLoading(false), 300);
    }
  };

  // 채널 변경 감지
  useEffect(() => {
    initializeRadio();
    // 채널 바뀔 때 이전 오디오 중지
    if (audioRef.current) {
        audioRef.current.pause();
        setIsPlaying(false);
    }
  }, [currentChannel]);

  // 로딩이 끝나고 트랙이 준비되면 오디오 설정 및 재생
  useEffect(() => {
    if (!loading && currentTrack && audioRef.current) {
      console.log("🎵 오디오 설정 및 재생 시도");
      audioRef.current.currentTime = initialOffset;
      
      // 브라우저 정책 대응 (사용자 클릭 후 재생 가능)
      audioRef.current.play()
        .then(() => setIsPlaying(true))
        .catch(err => console.log("▶ 재생을 위해 화면을 한 번 클릭해주세요."));
    }
  }, [loading, currentTrack]);

  // 볼륨 조절
  useEffect(() => {
    if (audioRef.current) audioRef.current.volume = volume;
  }, [volume]);

  const togglePlay = () => {
    if (!audioRef.current) return;
    if (isPlaying) audioRef.current.pause();
    else audioRef.current.play();
    setIsPlaying(!isPlaying);
  };

  const handleEnded = () => {
    setCurrentIndex((prev) => (prev + 1) % playlist.length);
  };

  // 렌더링 로직: 테두리가 사라지지 않도록 '틀'을 유지하는 방식
  return (
    <div className="radio-player" >
      {loading || !currentTrack ? (
        <div className="radio-player-loading">
          <p>📻 라디오 신호를 수신 중입니다... (ID: {currentChannel?.radioChannelId})</p>
        </div>
      ) : (
        <>
          <audio
            ref={audioRef}
            src={currentTrack.audioUrl}
            onEnded={handleEnded}
          />
          <RadioController
            isPlaying={isPlaying}
            onTogglePlay={togglePlay}
            volume={volume}
            onVolumeChange={setVolume}
            track={currentTrack}
          />
        </>
      )}
    </div>
  );
};

export default RadioPlayer;