import { Link, useParams} from "react-router-dom";
import { useState, useEffect} from "react";
import AxiosToken from "../../api/AxiosToken";
import "../../styles/postDetail.css";

const BoardDetail = () => {
  //경로에서 Id를 가져옴
  const { postId } = useParams();
  const [postData, setPostData] = useState();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // useEffect로 감싸기!
  useEffect(() => {
    const getData = async () => {
      try {
        setLoading(true);
        
        const response = await AxiosToken.get("/post/detail", {
          params: { postId: postId }
        });
        
        setPostData(response.data);
        
      } catch (err) {
        console.error('데이터 로딩 실패:', err);
        setError('게시글을 불러올 수 없습니다.');
      } finally {
        setLoading(false);
      }
    };

    getData();
  }, [postId]);  // postId가 바뀔 때만 실행

  // 로딩 중
  if (loading) {
    return <div className="loading">로딩중...</div>;
  }

  // 에러 발생
  if (error) {
    return <div className="error">{error}</div>;
  }
  // 데이터 없음
  if (!postData) {
    return <div className="not-found">게시글을 찾을 수 없습니다.</div>;
  }

  return (
    <div className="board-detail">
      {/* 제목 */}
      <h1 className="detail-title">{postData.postTitle}</h1>

      {/* 메타 정보 */}
      <div className="detail-meta">
        <span>작성자: {postData.postAuthor}</span>
        <span>작성일: {postData.postDate}</span>
        <span>조회수: {postData.postViews}</span>
      </div>

      {/* 본문 */}
      <div className="detail-content">
        {postData.postContent}
      </div>

      {/* 버튼 영역 */}
      <div className="detail-actions">
        <Link to="/post" className="btn gray">목록</Link>
        <div className="right">
          <Link to={`/post/edit/${postData.postId}`} className="btn gray">수정</Link>
          <button className="btn danger">삭제</button>
        </div>
      </div>
    </div>
  );
};

export default BoardDetail;
