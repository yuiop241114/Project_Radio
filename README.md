# 📻 Project_Radio
실시간 라디오 스트리밍 서비스
Spring Boot 기반 백엔드와 React 프론트엔드로 구현한
채널형 음악 스트리밍 웹 애플리케이션

# 📌 프로젝트 소개
RadioCast는 사용자가 개설한 라디오 채널을 통해
플레이리스트 기반의 음악을 실시간 라디오처럼 재생할 수 있는 웹 서비스입니다.

각 채널은 방송 시작 시점을 기준으로 재생 위치(offset)를 계산하여
접속 시점과 관계없이 동일한 트랙·동일한 시간대의 음악을 재생하도록 설계되었습니다.

# 🛠 기술 스택
## Backend

Java 17

Spring Boot

Spring Security + JWT 인증

JPA / MyBatis 혼합 사용

MySQL

RESTful API

## Frontend

React

React Router (Layout + Outlet 구조)

Axios (JWT 기반 API 통신)

HTML5 Audio API
