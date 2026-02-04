import "../../styles/radioChannelCreate.css";
import { useState } from "react";
import AxiosToken from "../../api/AxiosToken";

const RadioChannelCreate = () => {
  const [form, setForm] = useState({
    radioChannelName: "",
    radioUserId: localStorage.getItem("id"),
    description: ""
  });
  const [files, setFiles] = useState([]); // mp3 파일들

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
  e.preventDefault();

  const formData = new FormData();
  formData.append("radioChannelName", form.radioChannelName);
  formData.append("radioUserId", form.radioUserId);
  formData.append("description", form.description);

  for (let file of files) {
    formData.append("tracks", file);
  }

  try {
        await AxiosToken.post("/radio/channel", formData, {
          headers: { "Content-Type": "multipart/form-data"}
        });
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
            name="radioChannelName"
            value={form.radioChannelName}
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
          음원 파일 업로드 (ctrl키를 사용하여 여러 파일 선택 가능)
          <input
            type="file"
            accept="audio/mpeg"
            multiple
            onChange={(e) => setFiles(e.target.files)}
          />
        </label>

        <button type="submit">채널 생성</button>
      </form>
    </div>
  );
};

export default RadioChannelCreate;
