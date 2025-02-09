# redis는 0~15번따지의 database로 구성
select db번호

# DB내 모든 키 조회
keys *

---
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
---

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
---

# set자료구조
# set에 값 추가
sadd memberlist member1
sadd memberlist member2
sadd memberlist member2

# set 의 요소 조회
smembers memberlist
# set 요소의 개수 조회
scard memberlist
# set에서 멤버제거
srem memberlist member2
# 특정 요소가 set안에 들어있는지 확인
sismember memberlist member1

#redis set 활용 : 좋아요
# set 에 좋아요 추가
sadd likes:posting:1 member1
sadd likes:posting:1 member2
sadd likes:posting:1 member1
# 좋아요 개수
scard likes:posting:1
# 좋아요 눌렀는지 안눌렀는지 확인
sismember likes:posting:1 member1

---
# zset자료구조(Sorted Set)
zadd memberlist 3 member1
zadd memberlist 4 member2
zadd memberlist 1 member3
zadd memberlist 2 member4
# 조회방법 : 기본적으로 score 기준 오름차순정렬
zrange memberlist 0 -1
# 내림차순 정렬
zrevrange memberlist 0 -1
# zset member4 삭제
zrem memberlist member4
# 특정 멤버가 몇번째 순서인지 출력 (오름차순 기준)
zrank memberlist member1

# zset 활용 : 최근 본 상품 목록
# zset을 활용해서 최근시간순으로 score를 설정하여 중복없이 정렬,
zadd  1 recent:products 151930 pineapple
zadd  1 recent:products 152030 bananan
zadd  1 recent:products 153030 orange
zadd  1 recent:products 154030 apple
zadd  1 recent:products 155030 apple # zset 도 set이므로 같은 상품 add 시, 시간만 업데이트
# 최근 목록 순으로 3개 조회
zrevrange recent:products 0 2
# score까지 포함하여 전체 데이터 조회
zrevrange recent:products 0 -1 withscores
---

# hash : map형태의 자료구조, value값이 {key:value, key:value ...} 형태로 구성
hset member:info:1 name hong email hong@naver.com age 30
# 특정 요소 조회
hget member:info:1 name
# 모든 요소 조회
hgetall member:info:1
# 특정 요소 값만 수정 (JSON은 특정 요소값만 수정 못하고, JSON을 대체해야한다.)
hset member:info:1 name kim
# 특정 요소값의 값을 증가/감소 시킬경우
hincrby member:info:1 age 3 # 3 증가
hincrby member:info:1 age -3 # 3 감소

# redis hash 활용 예시 : 빈번하게 변경되는 객체값 캐싱
