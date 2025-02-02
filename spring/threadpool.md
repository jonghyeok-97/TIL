#### 스프링의 쓰레드 풀 우아한 종료

- 스프링 비동기를 위해 쓰레드 풀 사용시 ThreadPoolTaskExecutor 를 사용하자
  - 스프링의 생명주기에 맞게 ThreadPoolExecutor 를 구현한 것이다.

```angular2html
@Bean(value = "java", destroyMethod = "shutdown") ---> @Async 호출 시, 우아한 종료 X
public ExecutorService getJavaExecutor() {
    log.info("자바 쓰레드 풀 시작");
    ExecutorService es = Executors.newFixedThreadPool(5);
    return es;
}
@Bean("spring")   ---> @Async 호출 시, 우아한 종료 O
public Executor getSpringExecutor() {
    log.info("자바 쓰레드 풀 시작");
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize(5);
    return pool;
}
```

#### ThreadPoolTaskExecutor
- 우아한 종료 지원한다.
- 관련 메서드로는 setStrictEarlyShutdown() 가 있다.
  - 기본값: false  -> 쓰레드 풀의 우아한 종료
    - 컨텍스트 닫힘 후에도 작업 제출 허용 (관대한 설정).
    - 기존 작업은 라이프사이클 종료 단계에서 완료될 수 있음.
  - true로 설정할 경우   -> 
    - 컨텍스트 닫힘 시 즉시 종료 신호 전송.
    - 이후 작업 제출 거부 (엄격한 설정).
  - 단, 해당 설정들이 이 설정을 재정의할 수 있고, 이 경우 조율된 라이플사이클 종료 단계 없이 지연된 종료가 발생.
    - setAcceptTasksAfterContextClose: 조율된 라이프사이클 종료 단계를 우회.
    - setWaitForTasksToCompleteOnShutdown: 기존 작업 완료 대기 여부를 결정.