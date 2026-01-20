// export const getCurrentTrackInfo = (channel) => {
export const radioPlayLogic = (channel) => {
  const start = new Date(channel.startTime).getTime();
  const now = Date.now();
  let elapsed = Math.floor((now - start) / 1000);

  for (let i = 0; i < channel.playlist.length; i++) {
    if (elapsed < channel.playlist[i].duration) {
      return {
        trackIndex: i,
        offset: elapsed
      };
    }
    elapsed -= channel.playlist[i].duration;
  }

  return {
    trackIndex: 0,
    offset: 0
  };
};
