## `@Builder` 가 있는 Reservation Entity 의 ReservationBuilder 가 import 가 되지 않는 이유

#### **구현 과정**
* Reservation 엔티티에 롬복 어노테이션인 `@Builder` 를 추가하였고,
    * Reservation 를 생성하는 ReservationSaveRequest 에서 해당 Request 가 숙소와 멤버 엔티티에 의존할 필요가 없기 때문에
    * 의존관계를 끊어주기 위해
    * Reservation 를 생성하는데 필요한 멤버와 숙소를 Request 의 파라미터로 받지 않고,
    * 서비스에서 조합할 수 있도록 `ReservationBuilder` 를 반환하고자 하였다.
> **Reservation 엔티티 구현**
```
@Builder
@Entity
public class Reservation extends BaseTimeEntity {
}
```
> **ReservationSaveRequest 의 구현 내용**
```
import com.airdnb.clone.domain.common.Guest;
import com.airdnb.clone.domain.reservation.entity.Reservation;
import com.airdnb.clone.domain.reservation.entity.Reservation.ReservationBuilder;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ReservationSaveRequest {

    @NotNull
    private final Long memberId;

    @NotNull
    private final Long stayId;

    @Future
    private final LocalDateTime checkIn;

    @Future
    private final LocalDateTime checkOut;

    @Min(value = 0)
    private final Long totalRate;

    @Min(value = 1)
    private final Integer guestCount;

    public ReservationBuilder toBuilder() {
        return Reservation.builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalRate(totalRate)
                .guest(Guest.builder().guestCount(guestCount).build());
    }
}
```
* 그러자, `ReservationBuilder` 를 찾을 수 없다는 오류가 발생하였다.
  *  `static import not working in lombok builder in intelliJ`
---
### 에러 원인
* 롬복 어노테이션이 붙어 있는 자바 컴파일의 순서는 `compile -> AnnotationProcessor` 순서 이다.
    * 즉, 자바의 컴파일러가 먼저 코드를 컴파일을 시작한다. 이 때, 자바의 컴파일러가 `ReservationBuilder` 를 컴파일 하려고 할 때!!
    * 롬복 어노테이션(`@Builder`)에 의해 생성되는 `ReservationBuilder` 는 컴파일러 입장에서는 찾을 수 없는 대상이기 때문에 컴파일러가 찾을 수 없다고 말하는 것이다.

### 해결 과정
* 자바 컴파일러가 ReservationBuilder 를 찾을 수 있도록 Reservation 엔티티에 ReservationBuilder 껍데기를 추가하기
    * 껍데기를 추가만 하면, 롬복 어노테이션에 의해 ReservationBuilder 의 내부 구현에 해당하는 builder 패턴이 생성이 된다.
```
@Builder
@Entity
public class Reservation extends BaseTimeEntity {
   ...
   public static class ReservationBuilder{}
}
```

### 롬복 개발자는 javac 의 버그라고 말한다..?
* 해당 [stackoverflow](https://stackoverflow.com/questions/47674264/static-import-not-working-in-lombok-builder-in-intellij) 에 보면, 스스로가 롬복 개발자라고 말하는 사람은 javac 롬복이 아닌 javac(자바 컴파일러)의 버그라고 말하는데,,,?

### 회고
* 외부라이브러리의 장점은 확실히 보일러플레이트를 줄여주는 효과가 좋다는 것은 의심할 여지가 없다고 생각한다.
* 심지어, 스프링 dependency management 가 관리해주는 라이브러리에 대해서는 안정성에 의심할 여지가 없다고 생각했었다.
    * 스프링이 버전을 관리해줄만큼 널리 사용되고, 스프링 측에서 인정받았다고 생각했기 때문에.
* 하지만, 이번 일을 계기로 비즈니스 로직만큼은 외부 라이브러리에 대한 의존성을 줄이고, POJO 란 단어가 왜 생겨났는지 조금은(?) 이해하고, 외부 라이브러리에 대한 단점도 다시 한 번 생각해보는 계기가 되었다.
