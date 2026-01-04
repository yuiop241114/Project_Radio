import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import PostForm from "./PostForm";

const BoardEdit = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [board, setBoard] = useState(null);

  useEffect(() => {
    // TODO: GET /board/{id}
    // 임시 데이터
    setBoard({
      title: "기존 게시글 제목",
      content: "기존 게시글 내용입니다",
    });
  }, [id]);

  const handleUpdate = (data) => {
    console.log("게시글 수정:", id, data);

    // TODO: PUT /board/{id}
    alert("게시글이 수정되었습니다 (임시)");
    navigate(`/board/${id}`);
  };

  if (!board) return null;

  return (
    <PostForm
      mode="edit"
      initialData={board}
      onSubmit={handleUpdate}
    />
  );
};

export default BoardEdit;
