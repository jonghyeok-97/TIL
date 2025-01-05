#### 실행계획 소개
실행계획 포맷 표시 → `EXPLAIN FORMAT=TREE` `EXPLAIN FORMAT=JSON`  `EXPLAIN FORMAT=TABLE`
TREE 포맷의 실행 순서는 다음 기준으로 읽는다.
- 들여쓰기가 같은 레벨에서는 상단에 위치한 라인이 먼저 실행
- 들여쓰기가 다른 레벨에서는 가장 안쪽에 위치한 라인이 먼저 실행.

actual time ( 실제 소요된 시간)
- actual time=0.007..0.009 → 0.007은 첫 번째 레코드를 가져오는데 걸린 평균 시간, 0.009 는 마지막 레코드를 가져오는데 걸린 평균 시간

rows ( 처리한 레코드 건수)
- rows=10 → 테이블의 평균 레코드 건수

loops (반복 횟수)
- 테이블의 레코드를 찾는 작업이 반복된 횟수. s.emp_no = e.emp_no 쿼리에서 emp_no 에 해당하는 s.emp_no 를 찾는데, loops 가 233이라면 emp_no 의 갯수가 233이란 것이다.

actual time 과 rows 설명에서 평균 시간과 평균 레코드라 했는데, 이는 loops 필드의 값이 1 이상이기 때문이다. 즉 salaries 테이블에서 emp_no 일치 건을 찾는 작업을 233번 반복해서 실행했는데, 매번 salaries 테이블에서 첫 번쨰 레코드를 가져오는데 평균 0.007밀리초가 걸렸으며, 마지막 레코드를 읽는 데는 평균 0.009밀리초가 걸린 것이다.

#### 실행계획 분석
**id**
- 실행계획의 id 칼럼이 테이블의 접근 순서를 의미하지 않는다. → explain analyze 를 통해 접근 순서를 확인해야 한다.
- id 는 SELECT 쿼리별로 부여되는 식별자 값이다.

**select_type**
- 각 SELECT 쿼리가 어떤 타입의 쿼리인지 표시되는 칼럼.
- SIMPLE
    - UNION 이나 서브쿼리를 사용하지 않는 단순한 SELECT 쿼리(JOIN포함)
- PRIMARY
    - UNION 이나 서브쿼리를 가지는 SELECT 쿼리의 실행 계획에서 가장 바깥쪽에 있는 SELECT
- UNION
    - UNION 으로 결합하는 SELECT 중, 첫 번째를 제외한 두 번쨰 이후 SELECT 쿼리
    - UNION 으로 결합하는 첫 번째 쿼리는 DERIVED(UNION 결과를 모은 임시 테이블)로 표시된다.
- SUBQUERY
    - FROM절을 제외한 다른 절에서 실행된 SELECT 쿼리
    - FROM 절에서 실행된 SELECT 쿼리는 DERIVED 로 표시된다.
- DERIVED
    - SELECT 쿼리의 실행 결과로 메모리나 디스크에 임시 테이블을 생성하는 것을 의미한다.

쿼리를 튜닝하기 위해 실행 계획에 select_type 이 DERIVED 인 것이 있는지 확인하며, 서브 쿼리를 조인으로 해결하려고 강력히 권장한다.