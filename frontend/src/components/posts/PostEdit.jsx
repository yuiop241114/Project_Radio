import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import AxiosToken from "../../api/AxiosToken";
import PostForm from "./PostForm";

const BoardEdit = () => {
  const navigate = useNavigate();
  const { postId } = useParams(); //경로에 작성된 postId 추출
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [postData, setPostData] = useState(null);

  useEffect(() => {
    const getData = async () =>{
      try{
        setLoading(true);
        const response = await AxiosToken.get("/post/detail", {
          params : {postId : postId}
        });

        // console.log(response.data);
        setPostData(response.data);
      }catch(err){
        console.error('데이터 로딩 실패:', err);
        setError('게시글 데이터를 불러올수 없습니다.');
      }finally{
        setLoading(false);
      }
    }
    getData();
  }, [postId]);

  //수정 적용 submit
  const handleUpdate = async (editTitle, editContent) => {
    const response = await AxiosToken.put(`/post/edit/${postId}`,
      { 
        postTitle: editTitle, 
        postContent: editContent 
      }
    );
    navigate(`/post/detail/${postId}`);
  };

  if (!postData) return null;

  return (
    <PostForm
      mode="edit"
      initialData={postData}
      onSubmit={handleUpdate}
    />
  );
};

export default BoardEdit;
