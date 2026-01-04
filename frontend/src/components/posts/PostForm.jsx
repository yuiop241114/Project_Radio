import { useState, useEffect } from "react";
import "../../styles/postForm.css";

const BoardForm = ({ mode, initialData, onSubmit }) => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  // 수정 페이지일 경우 기존 데이터 세팅
  useEffect(() => {
    if (mode === "edit" && initialData) {
      setTitle(initialData.title);
      setContent(initialData.content);
    }
  }, [mode, initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ title, content });
  };

  return (
    <div className="board-form">
      <h1>{mode === "edit" ? "게시글 수정" : "게시글 작성"}</h1>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="제목을 입력하세요"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        <textarea
          placeholder="내용을 입력하세요"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />

        <div className="actions">
          <button type="submit" className="primary">
            {mode === "edit" ? "수정 완료" : "등록"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default BoardForm;
