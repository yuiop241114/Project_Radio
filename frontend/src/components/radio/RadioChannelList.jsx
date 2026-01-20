import React from "react";
import { radioChannels } from "./radioChannels";

const RadioChannelList = ({ currentChannel, onSelect }) => {
  return (
    <div className="channel-list">
      {radioChannels.map((channel) => (
        <div
          key={channel.id}
          className={`channel-item ${
            currentChannel.id === channel.id ? "active" : ""
          }`}
          onClick={() => onSelect(channel)}
        >
          <h3>{channel.name}</h3>
          <p>{channel.description}</p>
        </div>
      ))}
    </div>
  );
};

export default RadioChannelList;
