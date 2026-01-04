import { Link, useParams } from "react-router-dom";
import "../../styles/postDetail.css";

const BoardDetail = () => {
  const { id } = useParams();

  // 임시 데이터 (나중에 API 연동)
  const post = {
    title: "게시판 구조 공유합니다",
    author: "junsu",
    date: "2026-01-03",
    views: 128,
    content: `
React + Spring Boot 게시판 구조를
MainLayout + Outlet 기반으로 구성하면
확장성과 유지보수가 굉장히 좋아집니다.

JWT 인증과도 잘 어울립니다.
    `,
  };

  return (
    <div className="board-detail">
      {/* 제목 */}
      <h1 className="detail-title">{post.title}</h1>

      {/* 메타 정보 */}
      <div className="detail-meta">
        <span>작성자: {post.author}</span>
        <span>작성일: {post.date}</span>
        <span>조회수: {post.views}</span>
      </div>

      {/* 본문 */}
      <div className="detail-content">
        {post.content}
      </div>

      {/* 버튼 영역 */}
      <div className="detail-actions">
        <Link to="/post" className="btn gray">목록</Link>
        <div className="right">
          <Link to="/post/edit" className="btn gray">수정</Link>
          <button className="btn danger">삭제</button>
        </div>
      </div>
    </div>
  );
};

export default BoardDetail;
