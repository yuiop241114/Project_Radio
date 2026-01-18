import RadioInfo from "../components/radio/RadioInfo";
import RadioPlayer from "../components/radio/RadioPlayer";
import "../styles/radioPage.css";

const RadioPage = () => {
  return (
    <div className="radio-page">
      <RadioInfo />
      <RadioPlayer />
    </div>
  );
};

export default RadioPage;
