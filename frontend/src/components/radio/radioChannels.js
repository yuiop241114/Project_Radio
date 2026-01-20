//임시데이터
export const radioChannels = [
  {
    id: 1,
    name: "Radio Cast Live",
    description: "라디오캐스트 메인 방송",
    startTime: "2024-01-01T12:00:00",
    playlist: [
      {
        title: "Midnight Vibes",
        artist: "RadioCast",
        src: "/audio/track1.mp3",
        duration: 180
      },
      {
        title: "Night Drive",
        artist: "RadioCast",
        src: "/audio/track2.mp3",
        duration: 200
      }
    ]
  },
  {
    id: 2,
    name: "Lo-fi Channel",
    description: "집중용 로파이",
    startTime: "2024-01-01T12:00:00",
    playlist: [
      {
        title: "Lo-fi Breeze",
        artist: "DJ Chill",
        src: "/audio/track3.mp3",
        duration: 240
      }
    ]
  }
];
