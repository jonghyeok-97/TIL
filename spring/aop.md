@Aspect 를 빈으로 등록해줘야 한다.

- 어드바이저끼리 순서는 class 단위로만 된다. 메서드 X
- Order() 로 지정.

어드바이스 종류

- @Around
    - 메서드 호출 전후에 수행, 가장 강력한 어드바이스, **조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환** 등이 가능
- @Before
    - 조인 포인트 실행 이전에 실행
    - 예외가 발생하면 다음 코드가 호출되지 않는다.
- @AfterReturning
    - 조인 포인트가 정상 완료후 실행

    ```java
    @AfterReturning(value="hello.aop.order...", returning = "result")
    public void doReturn(JoinPoint joinpoint, Object result){}
    ```

    - returning 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
    - returning 절에 지정된 타입의 값을 반환하는 메서드만 실행된다.
    - @Around 와 다르게 반환되는 객체를 다른 객체로 변경할 순 없다. 하지만, 조작은 가능하다.
- @AfterThrowing
    - 메서드가 예외를 던지는 경우 실행
- @After
    - 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)

```java
@Around("hello.aop.order.Pointcuts.orderAndService()")
public Object doTx(ProceedingJoinPoint joinpoint) throws Throwable {
	try {
		// @Before
		log.info("...")
		Object result = joinPoint.proceed();
		// @AfterReturning
		log.info("...")
		return result;
	catch (Exception e) {
		// @AfterThrowing
		log.info("...")
		throw e;
	} finally {
		// @After
		log.info("...")
	}
}	
```

Returning, Throwing 은 반환 타입이 맞지 않으면(바인딩에 실패하면) 호출 되지도 않는다.

**어드바이스 순서**

- 동일한 @Aspect 안에서 동일한 조인포인트의 우선순위
- Around -> Before -> After -> AfterReturning -> AfterThrowing
- 순서를 이렇게 적용되지만, 호출 순서와 리턴 순서는 반대다.
- 물론 @Aspect 안에 동일한 종류의 어드바이스 2개가 있으면 순서 보장x
  -> inner class 로 @Aspect 를 분리하고 @Order 를 적용하자.