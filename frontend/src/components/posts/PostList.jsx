import { Link } from "react-router-dom";
import "../../styles/postList.css"

const BoardList = () => {
  const posts = [
    { id: 1, title: "첫 글입니다", author: "admin", date: "2026-01-01", views: 10 },
    { id: 2, title: "게시판 구조 공유", author: "junsu", date: "2026-01-02", views: 23 },
  ];

  return (
    <div className="board">
      <div className="board-top">
        <h2>자유게시판</h2>
        <Link to="/post/write" className="write-btn">글쓰기</Link>
      </div>

      <table className="board-table">
        <thead>
          <tr>
            <th>번호</th>
            <th className="title">제목</th>
            <th>작성자</th>
            <th>날짜</th>
            <th>조회</th>
          </tr>
        </thead>
        <tbody>
          {posts.map(post => (
            <tr key={post.id}>
              <td>{post.id}</td>
              <td className="title">
                {/* <Link to={`/post/write/${post.id}`}>{post.title}</Link> */}
                <Link to={`/post/detail`}>{post.title}</Link>
              </td>
              <td>{post.author}</td>
              <td>{post.date}</td>
              <td>{post.views}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BoardList;
