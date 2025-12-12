import React, {useState} from 'react'
import { useNavigate } from 'react-router-dom';
import axiosNoToken from './api/AxiosNoToken'

const SignUp = () => {
  //페이지 이동을 위한 hook
  const navigate = useNavigate();

  //사용자 입력 데이터 저장
  //useState의 경우 상태가 변경되면 자동으로 컴포넌트 재렌더링
  const [form, setForm] = useState({
    username: "",
    password: "",
    passwordConfirm: "", //비밀번호 확인을 위한 필드
    email: "",
  });

  //에러 메세지 저장
  const [error, setError] = useState("");

  //로딩 상태 표시를 위한 변수
  //기본은 false로 설정 true일 때 버튼 비활성화 및 로딩 텍스트 표시하는 방식으로 사용
  const [loading, setLoading] = useState(false);

  //입력 변화 처리
  //사용자가 입력할 때마다 실행되는 이벤트 핢수
  const handleChange = (e) => {
    setForm({
      ...form, //스프레드 문법 : form에 있는 모든 데이터를 가져와서 뿌림
      [e.target.name]: e.target.value, //변경된 필드만 업데이트
    });
  };

  //유효성 검사 함수
  const validateForm = () => {
    // 아이디 길이 검사
    if (form.username.length < 4) {
      setError("아이디는 4자 이상이어야 합니다.");
      return false;  // 검증 실패
    }

    // 아이디 영문+숫자만 허용
    const usernameRegex = /^[a-zA-Z0-9]+$/;
    if (!usernameRegex.test(form.username)) {
      setError("아이디는 영문과 숫자만 사용 가능합니다.");
      return false;
    }

    // 이메일 형식 검사 (정규표현식 사용)
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(form.email)) {
      setError("올바른 이메일 형식이 아닙니다.");
      return false;
    }

    // 비밀번호 길이 검사
    if (form.password.length < 8) {
      setError("비밀번호는 8자 이상이어야 합니다.");
      return false;
    }

    // 비밀번호 복잡도 검사 (영문, 숫자, 특수문자 포함)
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]/;
    if (!passwordRegex.test(form.password)) {
      setError("비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.");
      return false;
    }

    // 비밀번호 일치 확인
    if (form.password !== form.passwordConfirm) {
      setError("비밀번호가 일치하지 않습니다.");
      return false;
    }

    // 모든 검증 통과
    return true;
  };

  // 회원가입 요청
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      // 서버에 회원가입 요청 (POST)
      // await: 서버 응답을 기다림 (비동기 처리)
      const response = await axiosNoToken.post("/auth/signup", {
        username: form.username,
        password: form.password,
        email: form.email,
        // passwordConfirm은 서버에 보내지 않음 (클라이언트에서만 확인)
      });

      // 응답 상태 코드 확인 (200 또는 201은 성공)
      if (response.status === 200 || response.status === 201) {
        alert("회원가입이 완료되었습니다!");
        navigate("/login");  // 로그인 페이지로 이동
      }
    } catch (err) {
      // 에러 발생 시 처리
      console.error("회원가입 에러:", err);
      
      // 서버에서 보낸 에러 메시지가 있으면 표시, 없으면 기본 메시지
      if (err.response && err.response.data && err.response.data.message) {
        setError(err.response.data.message);
      } else {
        setError("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
      }
    } finally {
      // try-catch 완료 후 무조건 실행
      // 성공하든 실패하든 로딩 종료
      setLoading(false);
    }
  };

  return (
    <div id="singBox">
      <h2>회원가입</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="username" placeholder="아이디" value={form.username} onChange={handleChange} required/>

        <input type="email" name="email" placeholder="이메일" value={form.email} onChange={handleChange} required/>

        <input type="password" name="password" placeholder="비밀번호" value={form.password} onChange={handleChange} required/>

        {/*error  */}
        {error && <p style={  b0styles.error}>{error}</p>}

        <button type="submit">회원가입</button>
      </form>
    </div>
  );
};

export default SignUp;
