import { useNavigate } from "react-router-dom";
import AxiosToken from "../../api/AxiosToken";
import PostFrom from "./PostForm";

const BoardWrite = () => {
  const navigate = useNavigate();
  const postAuthor = localStorage.getItem("username");

  const handleCreate = async (editTitle, editContent) => {
      const response = await AxiosToken.post("/post/write",{
        postTitle:editTitle, postContent:editContent, postAuthor:postAuthor
      })
    // TODO: POST /board
    alert("게시글이 등록되었습니다");
    navigate("/post");
  };

  return <PostFrom mode="write" onSubmit={handleCreate} />;
};

export default BoardWrite;
