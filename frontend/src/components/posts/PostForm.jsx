import { useState, useEffect } from "react";
import "../../styles/postForm.css";

const BoardForm = ({ mode, initialData, onSubmit }) => {
  const [postTitle, setPostTitle] = useState();
  const [postContent, setPostContent] = useState();

  // 수정 페이지일 경우 기존 데이터 세팅
  useEffect(() => {
    if (mode === "edit" && initialData) {
      setPostTitle(initialData.postTitle);
      setPostContent(initialData.postContent);
    }
  }, [mode, initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({ postTitle, postContent });
  };

  return (
    <div className="board-form">
      <h1>{mode === "edit" ? "게시글 수정" : "게시글 작성"}</h1>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="제목을 입력하세요"
          value={postTitle}
          onChange={(e) => setPostTitle(e.target.value)}
          required
        />

        <textarea
          placeholder="내용을 입력하세요"
          value={postContent}
          onChange={(e) => setPostContent(e.target.value)}
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
