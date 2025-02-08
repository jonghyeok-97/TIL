# redis는 0~15번따지의 database로 구성
select db번호

# DB내 모든 키 조회
keys *

# 일반적인 String구조
# 저장
set user:email:1 hong1@naver.com
# nx: 존재하지 않으면 저장
set user:email:1 hong2@nave.com nx
# ex: 만료시간을 초단위로 지정(TTL)
set user:email:2 hong3@naver.com ex 10
# 저장된 key 에 TTL 부여
EXPIRE user:email:2 10
#특정 key 삭제
del user:email:1
# 현재 DB내 모든 key 삭제
flushdb

# redis활용 : 사용자 인증정보 저장(ex-refresh토큰)
set user:1:refresh_token ejhajcmmslc ex 100000

# redis활용 : 좋아요기능 구현
set likes:posting:1 0
incr likes:posting:1  # 1만큼 증가
decr likes:posting:1  # 1만큼 감소
get likes:posting:1

# redis활용 : 재고관리
set stocks:product:1 100
decr stocks:product:1

# redis활용 : 캐시 기능 구현
set posting:1 "{ \"title\":\"hello java\", \"contents\":\"hello java is...\", ...}" ex 100
