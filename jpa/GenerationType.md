### JPA가 제공하는 DB 기본 키 생성 전략
- 직접 할당: @Id
- 자동 할당: 대리 키 사용 방식
  - IDENTITY: 기본 키 생성을 DB에 위임
  - SEQUENCE: DB 시퀀스를 사용해서 기본 키를 할당
  - TABLE: 키 생성 테이블을 사용.

- Oracle은 시퀀스를 제공하지만 MySQL은 시퀀스를 제공하지 않는다.
- 즉, DBMS마다 지원하는 방식이 다르다.

#### IDENTITY 전략
- IDENTITY는 기본키를 DB에 위임한다
- MySQL, PostgreSQL, SQL Server, DB2에서 사용
- AUTO_INCREMENT 처럼 DB에 값을 저장하고 나서야 기본 키 값을 구할 수 있다.
  - 그래서, JPA는 기본키 값을 얻어오기 위해 DB를 추가로 조회한다.
  - 하이버네이트는 이를 최적화하여 INSERT 시점에 기본 키를 가져와서 DB와 1번만 통신한다.
- 엔티티가 영속상태가 되려면 식별자가 반드시 필요하다. 그래서, em.persist()를 호출하는 즉시 INSERT SQL이 DB에 전달된다. 따라서 이 전략은 트랜잭션을 지원하는 쓰기 지연이 동작하지 않는다.

#### SEQUENCE 전략
- DB 시퀀스는 유일한 값을 순서대로 생성하는 특별한 DB 오브젝트며 이 시퀀스를 사용해 기본 키를 생성한다.
- 시퀀스를 지원하는 오라클, PostgreSQL, DB2, H2 에서 사용할 수 있다.
- ```angular2html
  1. 테이블 만들기
  CREATE TABLE BOARD
  
  2. 시퀀스 생성
  CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1; 
  
  3. 엔티티 매핑
  @Entity
  @SequenceGenerator (               <-- 시퀀스 생성기이며, 시퀀스 생성기가 @Id에 식별자 값을 할당.
    name = "BOARD_SEQ_GENERATOR",
    sequenceName = "BOARD_SEQ",       <-- 매핑할 DB시퀀스 이름
    initialValue = 1, allocationSize = 1)
  public class Board {
  
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "BOARD_SEQ_GENERATOR")
    private Long id;
  }
  ```
  
- IDENTITY전략은 먼저 엔티티를 DB에 저장 후에 식별자를 조회
- 엔티티의 식별자에 할당

- SEQUENCE전략은 em.persist() 를 호출할 떄 먼저 DB시퀀스를 사용해서 식별자는 조회.
- 조회한 식별자를 엔티티에 할당 후 엔티티를 영속성 컨텍스트에 저장
- 트랜잭션 커밋해서 플러시가 발생하면 엔티티를 DB에 저장

