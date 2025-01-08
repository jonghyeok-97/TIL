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