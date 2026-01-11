import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import AxiosToken from "../../api/AxiosToken";

import "../../styles/postList.css"

const BoardList = () => {
  
  const [posts, setPosts] = useState([]);
  
  const getData = async () => {
    const postData = await AxiosToken.get("/post/list", {
      params : {size : 10}
    });
    setPosts(postData.data.postList);
  }

  useEffect(() => {
    getData();
  }, []);  // 빈 배열 = 컴포넌트 마운트 시 1번만 실행, 이거 없으면 무한 루프
  
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
          {posts.map((post) => (
            <tr key={post.postId}>
              <td>{post.postId}</td>
              <td className="title">
                {/* <Link to={`/post/write/${post.id}`}>{post.title}</Link> */}
                <Link to={`/post/detail`}>{post.postTitle}</Link>
              </td>
              <td>{post.postAuthor}</td>
              <td>{post.postDate}</td>
              <td>{post.postViews}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BoardList;
