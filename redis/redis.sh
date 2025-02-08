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

# List자료구조 (deque를 가짐)
# list 삽입, 제거
lpush hongildongs hong1
lpush hongildongs hong2
rpush hongildongs hong3
lrange hongildons 0 -1 (# 결과: hong2, hong1, hong3)
rpop hongildongs
lpop hongildongs
# list 조회
# -1 은 리스트의 끝자리(마지막 index)를 의미. -2는 끝에서 2번째를 의미
lrange hongildongs 0 0 #첫번째 값만 조회
lrange hongildongs -1 -1 #마지막 값만 조회
lrange hongildongs 0 -1
lrange hongildongs -2 -1 #마지막 2번째부터 마지막자리까지
lrange hongildongs 0 1 #첫 번쨰부터 2번쨰까지
# 데이터 개수 조회
llen hongildongs
# TTL 적용
EXPIRE hongildongs 20
# TTL 조회
ttl hongildongs
# redis 활용 : 최근 방문한 페이지, 최근 조회한 상품목록
rpush mypages www.naver.com
rpush mypages www.google.com
rpush mypages www.chatgpt.com
rpush mypages www.naver.com
#최근 방문한 페이지 3개만 보여주는
lrange mypages -3 -1