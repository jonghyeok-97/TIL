# ⭐서론
코드스쿼드 에어비앤비 클론 프로젝트 중 동시에 같은 숙소, 같은 날짜에 요청이 들어오는 환경을 구축하기 위해 멀티쓰레드 환경을 사용하고자 하였고, 그 과정에서의 AtomicInteger 와 CountDownLatch 의 사용법에 대해 소개합니다.
# ⭐소개
코드는 다음과 같습니다.
```
@Test
void 동시_예약_요청이_오더라도_1건만_예약된다() throws InterruptedException {
    // 예약 요청 생성
    LocalDateTime checkIn = LocalDateTime.of(LocalDate.of(2024, 11, 1), LocalTime.of(15, 0, 0));
    LocalDateTime checkOut = LocalDateTime.of(LocalDate.of(2024, 11, 2), LocalTime.of(12, 0, 0));
    // 멤버ID 가 1인 사람이 숙소ID가 1인 곳에 예약 요청
    BookingSaveRequest request = new BookingSaveRequest(1L, 1L, checkIn, checkOut, 2);

    /* 멀티 쓰레드 환경 구축을 위한 사전 준비 */
    AtomicInteger successCount = new AtomicInteger();
    AtomicInteger failCount = new AtomicInteger();

    long originCount = bookingRepository.count();
    int requestCount = 30;

    ExecutorService executorService = Executors.newFixedThreadPool(requestCount);
    CountDownLatch latch = new CountDownLatch(requestCount);

    /* 멀티 쓰레드 환경에서 로직 실행 */
    for (int i = 0; i < requestCount; i++) {
        executorService.execute(() -> {
            try {
                bookingService.create(request);
                successCount.incrementAndGet();
            } catch (Exception e) {
                failCount.incrementAndGet();
            } finally {
                latch.countDown();
            }
        });
    }
    latch.await();
    // 멀티쓰레드 환경 끝

    System.out.println("successCount = " + successCount);
    System.out.println("failCount = " + failCount);

    long changeCount = bookingRepository.count();
    Assertions.assertThat(originCount + 1).isEqualTo(changeCount);
}
```
#### 1. ExecutorService 의 `submit()` vs `execute()`
* 공통점
    * 쓰레드가 실행할 작업을 제출하는데 사용되는 메서드
* 차이점
    * submit 은 작업을 제출하고 반환 값으로 concurrent 패키지의 `Future` 객체를 반환하며, 반환된 `Future` 객체를 이용해서 작업 결과를 얻거나 예외를 직접 처리할 수 있습니다.
* 테스트 코드에서의 execute 선택 이유
    * 단순하게 submit 의 반환값인 `Future` 객체를 사용하지 않기 때문입니다.

#### 2. ExecutorService 의 `newFixedThreadPool` vs `newCachedThreadPool`
* newCachedThreadPool
    * 새로운 작업마다 새로운 쓰레드를 만들기 때문에, 적절한 쓰레드 개수를 설정해야 합니다.
    * 예를 들어 작업이 1000000개 일 경우, 1000000개의 쓰레드를 생성하여 리소스 낭비가 심하기 때문에 합리적인 쓰레드 수를 설정하는 것이 중요합니다.
    * 후에, 사용되지 않는 쓰레드는 제거됩니다.
* newFixeddThreadPool
    * 만료되지 않는 고정된 쓰레드 개수가 큐에 대기합니다.
    * 만약, 모든 쓰레드가 사용중일 때, 새로운 작업이 생기면 이 작업을 대기열에 추가합니다.
    * 사용되지 않는 쓰레드는 제거되지 않습니다.
    * 이를 통해 메모리, CPU같은 리소스 사용의 과도한 방지를 막을 수 있습니다.
* newFixedThreadPool 선택 이유
    * 테스트의 목적은 동시에 예약이 요청됐을 때, 1건만 예약이 되는지 확인하는 것으로 지속적인 요청이 들어오는 것이 아니기 때문에 어떤 쓰레드 풀을 사용하더라도 문제가 되지 않을 것입니다.

#### 3. `AtomicInteger` 소개
* 멀티쓰레드 환경에서 안전하게 정수 값을 업데이트 할 수 있게 도와줍니다.
* 만약, int 로 한다면 2개의 쓰레드가 동시에 successCount 에 +1 을 하는 경우 successCount 의 값은 +2 가 아닌 +1 만 올라가게 됩니다.

#### 4. `CountDownLatch` 소개
* 특정 쓰레드가 다른 쓰레드 작업이 완료될 때까지 기다릴 수 있게 해주는 객체입니다.
* 아래 코드에서, CountDownLatch 를 30으로 설정했습니다.
* finally 구문의 latch.countDown 를 사용해서 newFixedThreadPool 의 쓰레드가 종료될 때마다 30에서 카운트다운을 1씩 합니다.
* latch.await 을 통해 카운트가 0이 될 때까지 테스트 코드를 실행시킨 쓰레드가 latch.await 이후의 로직이 실행되지 않도록 기다리게 합니다.
    * 이를 통해, System.out.println 로직이 executorService 에 30개의 작업이 제출되기 전까지 실행되지 않습니다.
```
CountDownLatch latch = new CountDownLatch(requestCount);

...(중략)

for (int i = 0; i < requestCount; i++) {
    executorService.execute(() -> {
        try {
            bookingService.create(request);
            successCount.incrementAndGet();
        } catch (Exception e) {
            failCount.incrementAndGet();
        } finally {
            latch.countDown();
        }
    });
}
latch.await();
// 멀티쓰레드 환경 끝

System.out.println("successCount = " + successCount);
System.out.println("failCount = " + failCount);
```
---
* [submit vs execute](https://www.baeldung.com/java-execute-vs-submit-executor-service)
* [newFiexdThreadPool vs cachedThreadPool](https://www.baeldung.com/java-executors-cached-fixed-threadpool)