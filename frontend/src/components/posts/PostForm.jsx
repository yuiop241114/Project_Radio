import { useState, useEffect } from "react";
import "../../styles/postForm.css";

const BoardForm = ({ mode, initialData, onSubmit }) => {
  const [editTitle, setEditTitle] = useState();
  const [editContent, setEditContent] = useState();

  // 수정 페이지일 경우 기존 데이터 세팅
  useEffect(() => {
    if (mode === "edit" && initialData) {
      setEditTitle(initialData.postTitle);
      setEditContent(initialData.postContent);
    }
  }, [mode, initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(editTitle, editContent);
  };

  return (
    <div className="board-form">
      <h1>{mode === "edit" ? "게시글 수정" : "게시글 작성"}</h1>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="제목을 입력하세요"
          value={editTitle}
          onChange={(e) => setEditTitle(e.target.value)}
          required
        />

        <textarea
          placeholder="내용을 입력하세요"
          value={editContent}
          onChange={(e) => setEditContent(e.target.value)}
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
