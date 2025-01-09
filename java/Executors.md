ExecutorService 인터페이스의 기본 구현체는 ThreadPoolExecutor 이다.

ThreadPoolExecutor 생성자

- corePoolSize : 스레드 풀에서 관리되는 기본 스레드 수
- maximumPoolSize: 스레드 풀에서 관리되는 기본 스레드 수
- keepAliveTime, TimeUnit unit : 기본 스레드 수를 초과해서 만들어진 스레드가 생존할 수 있는 대기 시간이다. 이 시간동안 처리할 작업이 없다면 초과 스레드는 제거된다.
- BlockingQueue workQueue : 작업을 보관할 블로킹 큐


`ExecutorSerivce` 는 여러 작업을 한 번에 편리하게 처리하는 `invokeAll()`, `invokeAny()` 를 제공

**작업 컬렉션 처리**
**invokeAll()**

- <T> List<Future<T>> invokeAll(Collection<> tasks) throws InterrupedException
    - 모든 Callable 작업을 제출하고, 모든 작업이 완료될 때까지 기다린다.
- <T> List<Future<T>> invokeAll(Collection<> tasks, long timeout, Timeunit) throws InterrupedException
    - 지정된 시간 내에 모든 Callable 작업을 제출하고 완료될 때까지 기다린다.

invokeAny()

- <T> T invokeAny(Collection tasks) throws InterruptedException, ExecutionException
    - 하나의 Callable 작업이 완료될 때까지 기다리고, 가장 먼저 완료된 작업의 결과를 반환한다.
    - 완료되지 않은 나머지 작업은 취소한다.
- <T> T invokeAny(Collection tasks, long timeout, TimeUnit) throws InterruptedException, ExecutionException, TimeoutException
    - 지정된 시간 내에 하나의 Callable 작업이 완료될 때까지 기다리고, 가장 먼저 완료된 작업의 결과를 반환한다.
    - 완료되지 않은 나머지 작업은 취소한다.

#### ExecutorService 의 우아한 종료
`ExecutorService` 의 종료 메서드 → 잘 이해 안가면 13.pdf보자.

서비스 종료
- `void shutdown()`
  - 새로운 작업을 받지 않고, 이미 제출한 작업을 모두 완료한 후에 종료한다.
  - 논 블로킹 메서드(이 메서드를 호출한 스레드는 대기하지 않고, 다음 코드를 호출한다)
- `List<Runnable> shutdownNow()`
  - 실행 중인 작업을 중단하고, 대기 중인 작업을 반환하며 즉시 종료
  - 실행 중인 작업을 중단하기 위해 인터럽트를 발생
  - 논 블로킹 메서드


서비스 상태완료
- `boolean isShutdown()` → ExecutorService 가 종료되었는지 확인한다.
- `boolean isTerminated()` → shutdown()류 호출 후 모든 작업이 완료되었는지 확인한다

작업 완료 대기
- `boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException`
  - ExecutorService 종료 시 모든 작업이 완료될 때까지 대기한다. 이때 지정된 시간까지만 대기한다.
  - 블로킹 메서드

close()는 자바19부터 지원, shutdown()과 같다.
- shutdown()을 호출하고, 하루를 기다려도 작업이 완료되지 않으면 shutdownNow()를 호출한다.
- 호출한 스레드에 인터럽트가 발생해도 shutdownNow()를 호출한다.

#### 예외 정책
- AbortPolicy : 새로운 작업을 제출할 때, RejectedExecutionException 을 발생. 디폴트
- DiscardPolicy : 새로운 작업을 조용히 버린다.
- CallerRunPolicy : 새로운 작업을 제출한 스레드가 대신해서 직접 작업을 실행한다.
- 사용자 정의(RejectedExecutionHandler) : 개발자가 직접 정의한 거절 정책을 사용한다.