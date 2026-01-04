import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/postWrite.css";

const BoardWrite = () => {
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    // 👉 나중에 API 연동 예정
    console.log("게시글 등록:", { title, content });

    alert("게시글이 등록되었습니다 (임시)");
    navigate("/board");
  };

  return (
    <div className="board-write">
      <h1 className="write-title">게시글 작성</h1>

      <form onSubmit={handleSubmit} className="write-form">
        {/* 제목 */}
        <input
          type="text"
          className="write-input"
          placeholder="제목을 입력하세요"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />

        {/* 내용 */}
        <textarea
          className="write-textarea"
          placeholder="내용을 입력하세요"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          required
        />

        {/* 버튼 */}
        <div className="write-actions">
          <button
            type="button"
            className="btn gray"
            onClick={() => navigate(-1)}
          >
            취소
          </button>
          <button type="submit" className="btn primary">
            등록
          </button>
        </div>
      </form>
    </div>
  );
};

export default BoardWrite;
