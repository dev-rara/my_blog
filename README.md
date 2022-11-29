# my_blog
게시글을 작성하여 나만의 블로그를 만들 수 있는 Rest API  
[블로그 프로젝트 정보](https://dev-rara.notion.site/Blog-Project-6c0667ca7016470b8599a5599d8a92b4)
<br>

### 🛠️Tech Stack
* Spring Boot
* Spring JPA
* Lombok
* H2 Database  
<br>

### 💡구현 기능
#### 1. 전체 게시글 목록 조회 API
* 제목, 작성자명, 작성 내용, 작성 날짜 조회하기
* 작성 날짜 기준 내림차순으로 정렬하기

#### 2. 게시글 작성 API
* 제목, 작성자명, 비밀번호, 작성 내용 저장하기
* 저장된 게시글을 Client로 반환하기

#### 3. 선택한 게시글 조회 API
* 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용 조회하기

#### 4. 선택한 게시글 수정 API
* 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부 확인하기
* 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client로 반환하기

#### 5. 선택한 게시글 삭제 API
* 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부 확인하기
* 선택한 게시글을 삭제하고 Client로 성공했다는 표시 반환하기
