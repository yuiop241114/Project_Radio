import RadioPlayer from "../components/radio/RadioPlayer";
import RadioList from "../components/radio/RadioList";
import "../styles/radioPage.css";

const RadioPage = () => {
  return (
    <div className="radio-page">
      <RadioPlayer />
      <RadioList />
    </div>
  );
};

export default RadioPage;
