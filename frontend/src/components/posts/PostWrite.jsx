import { useNavigate } from "react-router-dom";
import PostFrom from "./PostForm";

const BoardWrite = () => {
  const navigate = useNavigate();

  const handleCreate = (data) => {
    console.log("게시글 작성:", data);

    // TODO: POST /board
    alert("게시글이 등록되었습니다 (임시)");
    navigate("/board");
  };

  return <PostFrom mode="write" onSubmit={handleCreate} />;
};

export default BoardWrite;
