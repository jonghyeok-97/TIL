- 인덱스
    - 인덱스는 정순으로 읽을 수도, 역순으로 읽을 수 있다.
    - 인덱스 생성 시점에 오름차순 또는 내림차순으로 정렬이 결정 되지만 쿼리가 그 인덱스를 사용하는 시점에 인덱스를 읽는 방향에 따라 오름차순 또는 내림차순 정렬 효과를 얻을 수 있다.

- 내림차순 인덱스
  - 복합 인덱스에서 인덱스 컬럼이 오름차순, 내림차순 혼합된 경우는 MySQL 8.0의 내림차순 인덱스로만 해결될 수 있다.
  - 오름차순 인덱스 : 작은 값의 인덱스 키가 B-Tree 왼쪽
      - CREATE index first_name ASC
  - 내림차순 인덱스 : 큰 값의 인덱스 키가 B-Tree 왼쪽
      - CREATE index first_name DECS
  - 인덱스 정순 스캔 : 인덱스 키에 상관없이 무조건 리프 노드의 왼쪽 → 오른쪽 탐색
      - ORDER BY ASC
  - 인덱스 역순 스캔 : 인덱스 키에 상관없이 무조건 리프 노드의 오른쪽 → 왼쪽 탐색 ORDER BY DECS
  
  - InnoDB에서 인덱스 정순 스캔이 역순 스캔보다 빠르다!!!
   그 이유는 2가지가 있다.,
     - 페이지 잠금이 인덱스 정순 스캔에 적합한 구조
      - 페이지 내에서 인덱스 레코드가 단방향으로만 연결된 구조라서 ←- 애매하다?

- 인덱스 걸 때는 꼭 = 등호가 먼저 오는 컬럼으로 하기.
- 유니크 인덱스
    - 유니크 인덱스는 쓰기 작업이 세컨더리 보다 더 느리다! 고유한지 확인도 해야하기 때문. 중복된 값을 체크할 때는 읽기 잠금을, 쓰기 할 때는 쓰기 잠금을 사용하며 이 과정에서 데드락이 아주 빈번히 발생한다.
- 외래키
  - 테이블의 변경(쓰기 잠금)이 발생하는 경우에만 잠금 경합이 발생한다.
  - 외래키와 연관되지 않은 칼람의 변경은 최대한 잠금 경합을 발생시키지 않는다.