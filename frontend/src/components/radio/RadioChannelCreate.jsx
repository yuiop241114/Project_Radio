import "../../styles/radioChannelCreate.css";
import { useState } from "react";
import AxiosToken from "../../api/AxiosToken";

const RadioChannelCreate = () => {
  const [form, setForm] = useState({
    name: "",
    description: "",
    playlistId: ""
  });

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await AxiosToken.post("/radio/channel", form);
      alert("📻 채널이 생성되었습니다!");
    } catch (err) {
      console.error(err);
      alert("채널 생성 실패");
    }
  };

  return (
    <div className="channel-create">
      <h2>📡 라디오 채널 생성</h2>

      <form className="channel-form" onSubmit={handleSubmit}>
        <label>
          채널 이름
          <input
            type="text"
            name="name"
            value={form.name}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          채널 설명
          <textarea
            name="description"
            value={form.description}
            onChange={handleChange}
          />
        </label>

        <label>
          플레이리스트 ID
          <input
            type="number"
            name="playlistId"
            value={form.playlistId}
            onChange={handleChange}
            required
          />
        </label>

        <button type="submit">채널 생성</button>
      </form>
    </div>
  );
};

export default RadioChannelCreate;
