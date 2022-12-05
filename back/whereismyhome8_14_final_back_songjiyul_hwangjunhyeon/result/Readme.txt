# 구현 완료 목록
- context-root : /myHome

## 1. 아파트 검색 - /apt
- 시도 리스트 뽑기 : /myHome/apt/sido
- 구군 이름 뽑기 : POST /myHome/apt/gugun
	- 필요한 정보만 담아서 Body로 DongDto를 보낸다
- 동 이름 뽑기 : POST /myHome/apt/dong
	- 필요한 정보만 담아서 Body로 DongDto를 보낸다
- 위도 경도 가져오기 : /myHome/apt/info/{aptCode}
- 아파트 이름으로 검색 : /myHome/apt/list/apt/{aptName}
- 동 별 검색(연도, 월 지정 or 미지정 가능) : /myHome/apt/list/dong/{keyword}
	- keyword : dongCode-dealYear-dealMonth 
		- 연도, 월 미지정 시 FrontEnd에서 0으로 보내기
- 거래 번호, 아파트 코드로 특정 데이터 상세 보기 : /myHome/apt/list/info/{keyword}
	- keyword : aptCode-housedeal.no

## 2. 회원 관리 및 로그인
- 로그인 : POST /myHome/login
	- LoginDto 로 id, password만 전달
- 로그아웃 : GET /myHome/logout
	- 세션에서 제거
- 회원 가입 : POST /myHome/user
- 회원 정보 수정 : PUT /myHome/user
- 회원 id로 조회 : GET /myHome/user/{id}
- 모든 유저 조회 : GET /myHome/userAll
- 비밀번호 찾기 : GET /myHome/userpassword/{id}

## 3. 관심 지역 관리 - 로그인된 상태를 가정 - /fav
- 현재 유저가 해당 지역 관심 지역으로 등록했는지 확인 : GET /myHome/fav/{dongCode} 
- 현재 유저 관심 지역 등록 : POST /myHome/fav/{dongCode}
- 현재 유저 관심 지역 제거 : DELETE /myHome/fav/{dongCode}
- 현재 유저가 관심있는 지역 모두 가져오기 : GET /myHome/fav/userFav