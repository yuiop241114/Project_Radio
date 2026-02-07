# 📻 Project_Radio

# 📌 프로젝트 소개
RadioCast는 사용자가 개설한 라디오 채널을 통해
플레이리스트 기반의 음악을 실시간 라디오처럼 재생할 수 있는 웹 서비스입니다.

각 채널은 방송 시작 시점을 기준으로 재생 위치(offset)를 계산하여
접속 시점과 관계없이 동일한 트랙·동일한 시간대의 음악을 재생하도록 설계되었습니다.

# 🛠 기술 스택
## Backend

Java 17

Spring Boot(Gradle)

Spring Security + JWT 인증

JPA 

MySQL

Redis

RESTful API

## Frontend

React

React Router (Layout + Outlet 구조)

Axios (JWT 기반 API 통신)


# 🧩 주요 기능
## 1️⃣ 라디오 채널 조회 및 선택

전체 라디오 채널 목록 조회

채널 선택 시 해당 채널 정보 및 현재 방송 상태 표시

채널별 플레이리스트 기반 음악 재생

## 2️⃣ 실시간 라디오 재생 로직

채널의 start_time을 기준으로 현재 재생 중인 트랙과 offset 계산

사용자가 언제 접속하든 동일한 방송 흐름을 유지

HTML5 <audio> 태그와 백엔드 스트리밍 API 연동

## 3️⃣ 음악 스트리밍 API

mp3 파일을 컨트롤러를 통해 직접 스트리밍

실제 파일 경로는 서버에만 저장하고
프론트엔드에는 API URL만 전달

## 4️⃣ 방송 상태 관리

라디오 채널의 방송 상태를 DB에서 관리

방송 중 / 방송 준비 중 상태에 따라 UI 분기

방송 시작 / 종료 시 상태 및 시작 시간 갱신

## 5️⃣ 라디오 채널 생성

새로운 라디오 채널 생성

플레이리스트 기반 채널 구성

채널 정보(이름, 설명, 재생 리스트) 관리

## 6️⃣ JWT 기반 인증 구조

로그인 시 JWT 발급

Redis에서 토큰 관리

Axios Interceptor를 활용한 토큰 자동 첨부

인증이 필요한 API 접근 제어

## 실제 서비스 화면
### 회원 기능 
![라디오 프로젝트 회원가입](https://github.com/user-attachments/assets/aee33868-759d-427f-955a-2f63626e47eb)

### 게시판(전체 조회, 게시글 생성 및 수정, 삭제)
![라디오 프로젝트 게시글 생성](https://github.com/user-attachments/assets/3c958b61-79ab-475a-be21-8be9987a2cf7)
![라디오 프로젝트 게시글 수정](https://github.com/user-attachments/assets/6020d574-6e55-4c4c-a10c-6fb3fcfeb30b)
![라디오 프로젝트 게시글 삭제](https://github.com/user-attachments/assets/e8a0421a-0191-4840-a629-1272410178b4)


### 라디오 기능
![라디오 프로젝트 라디오 채널 생성](https://github.com/user-attachments/assets/0d61547b-bdac-4a1a-884e-f35be519d07c)
![라디오 프로젝트 라디오 채널 관리(방송 상태)](https://github.com/user-attachments/assets/f1601a5e-e320-4d36-9666-e52483dc22a3)

