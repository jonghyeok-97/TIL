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