# 📅 캘린더 프로젝트
사용자는 일정을 등록하고, 월별로 일정을 확인하며, 각 일정에 댓글을 남길 수 있습니다.

---

## ✅ 주요 기능
| 기능             | 설명 |
|------------------|------|
| 📅 **월별 일정 확인** | 캘린더 페이지(`/calendar`)에서 월별로 등록된 일정 확인 |
| ➕ **일정 등록**       | 일정 제목, 내용, 날짜를 입력해 생성 가능 |
| 📌 **일정 상세 보기**   | 일정 클릭 시 내용과 댓글 확인 가능 |
| 💬 **댓글 등록/삭제** | 일정 상세 페이지에서 댓글 작성 및 삭제 가능 |
| ❌ **일정 삭제**       | 일정 상세 페이지에서 삭제 버튼 제공 |

---

## 🌐 URL 구조
| URL                          | 메서드 | 설명               |
|------------------------------|--------|--------------------|
| `/calendar`                  | GET    | 월별 일정 리스트    |
| `/schedule/write`           | GET    | 일정 등록 폼        |
| `/schedule/write`           | POST   | 일정 등록 처리      |
| `/schedule/detail?id={id}`  | GET    | 일정 상세 페이지     |
| `/schedule/delete?id={id}`  | DELETE | 일정 삭제           |
| `/comment/write`            | POST   | 댓글 등록 처리      |
| `/comment/delete?id=...`    | DELETE | 댓글 삭제 처리      |

---

# 📄 API 명세서

## 1. 일정(Schedule) API

### 1.1 일정 생성

| 항목 | 내용 |
|------|------|
| **URL** | POST /schedules |
| **Request Body** | title (String, 필수, 최대 30자)<br>content (String, 필수, 최대 200자)<br>writer (String, 필수)<br>password (String, 필수) |
| **Response** | 200 OK |
| **Response Body** | id, title, content, writer, createdAt |
| **Error** | 400 Bad Request - 필수값 누락, 글자수 초과 |

### 1.2 일정 목록 조회

| 항목 | 내용 |
|------|------|
| **URL** | GET /schedules |
| **Request** | 없음 |
| **Response** | 200 OK |
| **Response Body** | 일정 배열(id, title, writer, createdAt) |

### 1.3 일정 단건 조회

| 항목 | 내용 |
|------|------|
| **URL** | GET /schedules/{id} |
| **Request** | 없음 |
| **Response** | 200 OK |
| **Response Body** | id, title, content, writer, createdAt |

### 1.4 일정 수정

| 항목 | 내용 |
|------|------|
| **URL** | PUT /schedules/{id} |
| **Request Body** | title (String, 선택)<br>writer (String, 선택)<br>password (String, 필수) |
| **Response** | 200 OK |
| **Error** | 401 Unauthorized - 비밀번호 불일치<br>400 Bad Request - 필수값 누락 |

### 1.5 일정 삭제

| 항목 | 내용 |
|------|------|
| **URL** | DELETE /schedules/{id} |
| **Request Body** | password (String, 필수) |
| **Response** | 204 No Content |
| **Error** | 401 Unauthorized - 비밀번호 불일치<br>404 Not Found - 일정 없음 |

---

## 2. 댓글(Comment) API

### 2.1 댓글 작성

| 항목 | 내용 |
|------|------|
| **URL** | POST /schedules/{id}/comments |
| **Request Body** | comment (String, 필수, 최대 100자)<br>writer (String, 필수)<br>password (String, 필수) |
| **Response** | 201 Created |
| **Response Body** | id, comment, writer, createdAt |
| **Error** | 400 Bad Request - 글자수 초과, 누락 |

### 2.2 댓글 수정

| 항목 | 내용 |
|------|------|
| **URL** | PUT /schedules/{id}/comments/{commentId} |
| **Request Body** | comment (String, 필수)<br>password (String, 필수) |
| **Response** | 200 OK |
| **Error** | 401 Unauthorized - 비밀번호 불일치<br>404 Not Found - 댓글 없음 |

### 2.3 댓글 삭제

| 항목 | 내용 |
|------|------|
| **URL** | DELETE /schedules/{id}/comments/{commentId} |
| **Request Body** | password (String, 필수) |
| **Response** | 204 No Content |
| **Error** | 401 Unauthorized - 비밀번호 불일치<br>404 Not Found - 댓글 없음 |


---

### ⚠️ 공통 에러 코드

| 상태 코드 | 설명 |
|-----------|------|
| 400 | 잘못된 요청 (파라미터 누락, 형식 오류 등) |
| 401 | 비밀번호 불일치 |
| 404 | 존재하지 않는 리소스 요청 |
| 409 | 중복된 데이터 존재 |


---

# 🗂 도메인 구조

## 📝 Schedule

| 필드명    | 타입           | 설명       |
|-----------|----------------|------------|
| id        | Long           | PK         |
| title     | String         | 일정 제목  |
| content   | String         | 일정 내용  |
| writer    | String         | 작성자명   |
| password  | String         | 비밀번호   |
| createdAt | LocalDateTime  | 생성 시각  |
| updatedAt | LocalDateTime  | 수정 시각  |

---

## 💬 Comment

| 필드명    | 타입           | 설명               |
|-----------|----------------|--------------------|
| id        | Long           | PK                 |
| schedule  | Schedule       | 연관 일정 (FK)     |
| comment   | String         | 댓글 내용          |
| writer    | String         | 댓글 작성자        |
| password  | String         | 비밀번호           |
| createdAt | LocalDateTime  | 댓글 생성 시각     |
| createdAt | LocalDateTime  | 댓글 수정 시각     |

