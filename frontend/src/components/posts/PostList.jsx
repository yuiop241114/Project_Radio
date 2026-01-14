import { Link } from "react-router-dom";
import { useState, useEffect, useRef} from "react";
import AxiosToken from "../../api/AxiosToken";

import "../../styles/postList.css"

const BoardList = () => {
  
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [cursor, setCursor] = useState(null);
  const [hasNext, setHasNext] = useState(true);
  
  //관찰할 요소에 대한 ref(usestatus와 같은 기능이지만 리랜더링 X)
  const observerTarget = useRef(null);

  //게시글 리스트를 가져오는 함수
  const getData = async () => {
    if(loading || !hasNext) return;

    setLoading(true);
    
    try{
      const postData = await AxiosToken.get("/post/list", {
        params : {cursor : cursor, size : 10}
      });

      setPosts(prev => [...prev, ...postData.data.postList]);
      setCursor(postData.data.nextCursor);
      setHasNext(postData.data.hasNext)

    }catch (error) {
      console.error("데이터 로딩 실패:", error);
    } finally {
      setLoading(false);
    }
  }

  //Intersection Observer 설정
  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        // 관찰 대상이 화면에 보이면
        if (entries[0].isIntersecting && hasNext && !loading) {
          getData();  // 다음 데이터 로딩
        }
      },
      { threshold: 0.5 }  // 50% 보이면 실행
    );
    
    if (observerTarget.current) {
      observer.observe(observerTarget.current);
    }
    
    return () => {
      if (observerTarget.current) {
        observer.unobserve(observerTarget.current);
      }
    };
  }, 
  [hasNext, loading, cursor]);  // 의존성 배열

  // useEffect(() => {
  //   getData();
  // }, []);  // 빈 배열 = 컴포넌트 마운트 시 1번만 실행, 이거 없으면 무한 루프
  
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
                <Link to={`/post/detail/${post.postId}`}>{post.postTitle}</Link>
              </td>
              <td>{post.postAuthor}</td>
              <td>{post.postDate}</td>
              <td>{post.postView}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* 이 요소가 화면에 보이면 다음 데이터 로딩 */}
      <div ref={observerTarget} style={{ height: '50px', margin: '20px' }}>
        {loading && <div>로딩중...</div>}
        {!hasNext && <div>마지막 게시글입니다.</div>}
      </div>
    </div>
  );
};

export default BoardList;
