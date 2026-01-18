import React, { useRef, useState , useEffect} from "react";
import playlist from "./playlist";
import RadioController from "./RadioController";

const RadioPlayer = () => {
  const audioRef = useRef(null);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [volume, setVolume] = useState(0.7);

  const currentTrack = playlist[currentIndex];

  useEffect(() => {
    if (!audioRef.current) return;
    audioRef.current.volume = volume;
  }, [volume]);

  const togglePlay = () => {
    if (!audioRef.current) return;

    if (isPlaying) {
      audioRef.current.pause();
    } else {
      audioRef.current.play();
    }
    setIsPlaying(!isPlaying);
  };

   // 곡 끝나면 자동 다음 곡
  const handleEnded = () => {
    setCurrentIndex((prev) => (prev + 1) % playlist.length);
    setIsPlaying(true);
  };

  // 곡 변경 시 자동 재생
  useEffect(() => {
    if (!audioRef.current) return;
    if (isPlaying) audioRef.current.play();
  }, [currentIndex]);

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
