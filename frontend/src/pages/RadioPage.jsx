import { useState } from "react";
import RadioInfo from "../components/radio/RadioInfo";
import RadioChannelList from "../components/radio/RadioChannelList";
import RadioPlayer from "../components/radio/RadioPlayer";
import { radioChannels } from "../components/radio/radioChannels";
import "../styles/radioPage.css";

const RadioPage = () => {
  const [currentChannel, setCurrentChannel] = useState(radioChannels[0]);

  return (
    <div className="radio-layout">
      <RadioChannelList
        currentChannel={currentChannel}
        onSelect={setCurrentChannel}
      />

      <div className="radio-main">
        <RadioInfo channel={currentChannel} />
        <RadioPlayer channel={currentChannel} />
      </div>
    </div>
  );
};

export default RadioPage;
