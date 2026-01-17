import RadioPlayer from "../components/radio/RadioPlayer";
import FrequencyList from "../components/radio/FrequencyList";
import "../styles/radioList.css";

const RadioPage = () => {
  return (
    <div className="radio-page">
      <RadioPlayer />
      <FrequencyList />
    </div>
  );
};

export default RadioPage;
