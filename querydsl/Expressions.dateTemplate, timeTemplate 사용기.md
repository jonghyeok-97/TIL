> Querydsl 의 기본 사용법은 소개하지 않습니다.
> Querydsl 을 학습 중에 작성한 글이기 때문에 소개한 방법이 최고의 방법이 아닐 수 있음을 알려드립니다🙇‍♂️
---
* [블로그 전문 링크](https://dkswhdgur246.tistory.com/45)
---
## ⭐서론
> 에어비앤비 서비스의 검색 기능(필터 기능)을 구현하고자 했습니다.
* 사용자가 6/19 ~ 6/20 를 필터 조건으로 등록하면,
* 서버에서는 6/19 ~ 6/20 까지 이용 가능 한 숙소(예약 되어있지 않은 숙소)에 해당하는 ID 를 찾습니다

* 예를 들어,
    * 숙소 ID가 1인 숙소에 `6/19 PM15:00 ~ 6/20 AM11:00` 인 예약이 있다고 가정합니다.
    * 만약, 사용자가 6/20 ~ 6/21 에 이용가능한 숙소를 찾는다면
        * ID가 1인 숙소의 체크인 시간은 PM 15:00, 체크아웃 시간은 AM 11:00 이기 때문에,
        * ID가 1인 숙소는 6/20 ~ 6/21 에 이용가능하도록 합니다.
* 이를 위해, 날짜는 날짜대로 시간은 시간대로 각각 비교하고자 합니다.
## ⭐구현
* 예약 엔티티 소개
    * JPA 의 ddl-auto 기능을 추가하면 예약 엔티티의 LocalDateTime 은 JPA 가 Mysql 에 DateTime 으로 저장이 됩니다.
```
@Entity
public class Reservation {

    @Column(name = "CHECK_IN")
    private LocalDateTime checkIn;

    @Column(name = "CHECK_OUT")
    private LocalDateTime checkOut;
    
}
```
#### 사용자가 입력한 6/19 ~ 6/20 에 해당하는 예약 불가능한 숙소 Id 를 찾기
* Querydsl 의 Expressions  라이브러리에 있는 `timeTemplate` 과 `dateTemplate` 을 사용해서 시간, 날짜별로 비교합니다.
* `Expressions.timeTemplate(LocalTime.class, "CAST({0} AS TIME)", reservation.checkIn);` 해석
    * `CAST({0} AS TIME)` -> Mysql 의 DateTime 을 Time 으로 형변환 합니다.
        * {0}은 자리 표시자로, 후에 제공된 파라미터(reservation.checkIn)로 대체됩니다.
    *  형변환 Time 을 자바의 `LocalTime` 으로 받습니다.

* `Expressions.timeTemplate` 예시
```
// 예약 checkIn시간(타입 : LocalDateTime) Time 로 형변환하여, LocalTime 으로 만듭니다.
TimeTemplate<LocalTime> reservationCheckInTime 
        = Expressions.timeTemplate(LocalTime.class, "CAST({0} AS TIME)", reservation.checkIn);
```
* `Expressions.dateTemplate` 예시
```
// 예약 checkout 시간(타입 : LocalDateTime)을 Date 로 형변환하여, LocalDate로 만듭니다.
DateTemplate<LocalDate> reservationCheckOutDate 
        = Expressions.dateTemplate(LocalDate.class, "CAST({0} AS DATE)", reservation.checkOut);
```


* 전체 코드
```
@RequiredArgsConstructor
public class ReservationQuerydslImpl implements ReservationQuerydsl{

    private final JPAQueryFactory jpaQueryFactory;

    public List<Long> findUnavailableStayIdsByCheckInOut(LocalDate checkInDate, LocalDate checkOutDate) {
        return jpaQueryFactory
                .select(reservation.stay.id)
                .distinct()
                .from(reservation)
                .where(matchesCheckInAndCheckOut(checkInDate, checkOutDate))
                .fetch();
    }

    private BooleanExpression matchesCheckInAndCheckOut(LocalDate checkInDate, LocalDate checkOutDate) {

        if (checkInDate == null || checkOutDate == null) {
            return null;
        }
        if (checkInDate.isAfter(checkOutDate)) {
            throw new IllegalArgumentException();
        }

        DateTemplate<LocalDate> reservationCheckInDate = Expressions.dateTemplate(LocalDate.class,
                "CAST({0} AS DATE)", reservation.checkIn);

        TimeTemplate<LocalTime> reservationCheckInTime = Expressions.timeTemplate(LocalTime.class,
                "CAST({0} AS TIME)", reservation.checkIn);

        DateTemplate<LocalDate> reservationCheckOutDate = Expressions.dateTemplate(LocalDate.class,
                "CAST({0} AS DATE)", reservation.checkOut);

        TimeTemplate<LocalTime> reservationCheckOutTime = Expressions.timeTemplate(LocalTime.class,
                "CAST({0} AS TIME)", reservation.checkOut);

     /*
     *  (이미 예약 된 체크인 날짜 < 검색한 체크아웃 날짜) or
     *  (이미 예약된 체크인 날짜 == 검색한 체크아웃 날짜) and (이미 예약된 숙소의 체크인 시간 < 해당 숙소의 체크아웃 시간)
     *  and
     *  (이미 예약된 체크아웃 날짜 > 검색한 체크인 날짜) or
     *  (이미 예약된 체크아웃 날짜 == 검색한 체크인 날짜) and (이미 예약된 숙소의 체크아웃 시간 > 해당 숙소의 체크인 시간)
     * 에 해당되면 예약 불가능한 숙소에 해당.
     */

      return  reservationCheckInDate.lt(checkOutDate)
                .or(reservationCheckInDate.eq(checkOutDate).and(reservationCheckInTime.lt(reservation.stay.checkOutTime)))             
                .and(reservationCheckOutDate.gt(checkInDate)
                     .or(reservationCheckOutDate.eq(checkInDate).and(reservationCheckOutTime.gt(reservation.stay.checkInTime))));
    }
}
```