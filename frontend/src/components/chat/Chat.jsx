import { useEffect, useState } from "react";
// import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import axiosToken from "../../api/AxiosToken";

import "../../styles/chat.css";
const Chat = ({ radioChannelId }) => {

  const [client, setClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {

    if (!radioChannelId) return;
  
    // const socket = new SockJS("http://localhost:8081/ws-chat");
    //라디오 채널 입장 시 기존 채팅 내역 조회(20개)
    setMessages([]); //이전 채널 채팅 초기화 후 진행

    const chatHistory = async () => {
      const history = await axiosToken.get("/chat/history",
        {params : {radioChannelId : radioChannelId} }
      )
      console.log(history.data);
      setMessages((prev) => [...prev, ...history.data]);
    };

    const token = localStorage.getItem("accessToken");
    const stompClient = new Client({
      // webSocketFactory: () => new SockJS("http://localhost:8081/ws-chat"),
      brokerURL: "ws://localhost:8081/ws-chat",
      connectHeaders: {
        Authorization: `Bearer ${token}`
      },
      onConnect: () => {
        console.log("웹소켓 연결 성공");
        chatHistory();
        stompClient.subscribe(`/topic/chatChannel/${radioChannelId}`, (msg) => {
          const chatMessage = JSON.parse(msg.body);
          // console.log(chatMessage);
          setMessages((prev) => [...prev, chatMessage]);
        });
      },
        
        onStompError: (frame) => {
        console.error("STOMP 에러:", frame);
        }
      });

    stompClient.activate();
    setClient(stompClient);

    return () => {
      stompClient.deactivate();
    };

  }, [radioChannelId]);


  const sendMessage = () => {
    if (!client || message.trim() === "") return;
    // console.log("메세지 변수", message)
    client.publish({
      destination: "/app/chat/send",
      body: JSON.stringify({
        radioChannelId: radioChannelId,
        // sender: localStorage.getItem("username"),
        content: message
      })
    });

    setMessage("");
  };


  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };
  
  return (
   <div className="chat-container">

      <div className="chat-header">
        💬 방송 채팅
      </div>

      <div className="chat-box">

        {messages.map((msg, index) => (
          <div key={index} className="chat-message">
            <span className="chat-sender">{msg.sender}</span>
            <span className="chat-content">{msg.content}</span>
          </div>
        ))}

      </div>

      <div className="chat-input-area">

        <input
          className="chat-input"
          type="text"
          value={message}
          onChange={(e)=>setMessage(e.target.value)}
          onKeyDown={handleKeyPress}
          placeholder="채팅을 입력하세요"
        />

        <button
          className="chat-send-btn"
          onClick={sendMessage}
        >
          전송
        </button>

      </div>

    </div>
  );
};

export default Chat;