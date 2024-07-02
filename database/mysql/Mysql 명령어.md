# ⭐MySQL 명령어
#### **MySQL의 모든 사용자 조회**
`SELECT User, Host FROM mysql.user;`
#### **컬럼에 인덱스 추가**
```
CREATE INDEX [인덱스 별칭] ON [테이블 명]([컬럼 명]);
ex) CREATE INDEX gromit_index ON codeSquad(nickname);
```
#### **외래키로 걸린 column 이 포함된 인덱스, 유니크 키 삭제하기**
1. 외래키 걸린 column 에서 외래키 제약 조건을 먼저 삭제
2. 인덱스 or 유니크 키 삭제
3. 외래키가 걸렸던 column 에 외래키 제약 조건을 다시 추가.
```
ALTER TABLE [테이블 명] DROP FOREIGN KEY [외래키 명];
DROP INDEX [인덱스 명] ON [테이블 명];
ALTER TABLE [테이블 명] ADD CONSTRAINT [추가할 외래키 명] FOREIGN KEY ([외래키 컬럼명]) REFERENCES [참조 테이블 명] ([참조 테이블 컬럼명])
```
* 이 방식은 외래키를 삭제하고 추가하는 과정에서 외래키 제약 조건이
* 없기 때문에, 외래키 제약 조건에 위반하는 레코드가 삽입 될 수 있다.
* 그러니, 해당 테이블에 테이블 락을 걸어야 할 듯 하다.
---
#### **csv 파일에 있는 데이터를 테이블에 쓰기**
단, csv파일의 컬럼명과 테이블의 컬럼명은 일치해야 함.
```
LOAD DATA INFILE '/var/lib/mysql-files/stays.csv' // 파일 경로 
INTO TABLE [테이블명] 
FIELDS TERMINATED BY ','  // 각 필드가 쉼표로 구분됨을 의미
ENCLOSED BY '"'  // 각 필드가 큰따옴표(") 로 감싸져있음을 의미
LINES TERMINATED BY '\n'  // 각 행이 개행 문자로 끝남을 의미
IGNORE 1 ROWS  // CSV파일의 첫 행을 무시,
(컬럼명1, 컬렴명2, ... , @lat, @lon)  // csv파일의 컬럼명 적기
SET POINT = ST_GeomFromText(CONCAT('POINT(', @lat,' ', @lon, ')'), 4326); // 지리 좌표(위도, 경도)인 POINT 가 컬럼에 있을 때 사용
```