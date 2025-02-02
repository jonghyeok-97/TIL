포인트컷 지시자(Pointcut Designator) 종류

- `execution` : 메소드 실행 조인 포인트를 매칭한다. AOP에서 가장 많이 사용하고, 기능도 복잡하다
    - execution(접근제어자? **반환타입** 선언타입?**메서드이름(파라미터)** 예외?)
    - `*public String hello.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)*`
        - 접근제어자? : public
        - 반환타입 : String
        - 선업타입? : hello.aop.order.aop.member.MemberServiceImpl
        - 메서드이름 : hello
        - 파라미터 : (String)
        - 예외? : 생략
    - ?는 생략할 수 있다.
    - * 같은 패턴을 지정할 수 있다.
        - 패키지에서 . 과 .. 의 차이를 이해하자
            - . 은 정확하게 해당 위치의 패키지
            - .. 은 해당 위치의 패키지와 그 하위 패키지도 포함
    - execution 에 MemServiceImpl 대신 MemberService 인 부모타입을 선언하면 MemberServiceImpl 에 오버라이딩된 메서드에만 aop 가 적용된다.
    - execution 파라미터 매칭 규칙
        - (String) : 정확하게 String 타입 파라미터
        - () : 파라미터가 없어야 한다.
        - (*) : 정확히 하나의 파라미터, 단 모든 타입을 허용
        - (*, *) : 정확히 2개의 파라미터, 단 모든 타입을 허용
        - (..) : 숫자와 무관하게 모든 파라미터, 모든 타입을 허용, 파라미터가 없어도 된다.
        - (String, ..) : String 타입으로 시작해야한다. 숫자와 무관하게 모든 파라미터, 모든 타입을 허용.
            - (String), (String, Xxx), (String, Xxx, Xxx) 허
- `within`  : 해당 타입이 매칭되면 그 안의 메서드들이 자동으로 매칭. execution 에서 타입 부분만 사용한다고 보면 된다.
    - `within(*hello.aop.order.aop.member.MemberServiceImpl)*`
    - 단, execution 과 다르게 인터페이스 매칭 불가.
- `args`  : 파라미터의 런타임 인스턴스(상위 타입 허용)를 기준으로 매칭한다. execution 은 파라미터의 시그니처로 매칭함.
    - 단독으로 사용되기 보다는 파라미터 바인딩에서 주로 사용.
- `@target`  :  클래스 어노테이션이 붙은 모든 인스턴스(부모, 자식)과 매칭
    - `@Around(@within(hello.aop.member.ClassAop))`
- `@within` : 클래스 어노테이션이 붙은 해당 인스턴스만 매칭
    - @target vs @within
        - target 은 인스턴스의 모든 메서드를 조인포인트로 적용.
        - within 은 해당 타입 내에 있는 메서드만 조인 포인트로 적용.
        - 그림처럼 자식에 aop를 걸 때 target 은 부모 까지 aop 적용, within 은 자식만 적용

- args, @args, @target 과 같은 포인트 컷 지시자는 단독으로 사용하면 안된다!
    - args, @args, @target 은 실체 객체 인스턴스가 생성되고 실행될 때 어드바이스 적용 여부를 확인할 수 있다.
    - 실행 시점(실제 인스턴스의 메서드 호출)에 일어나는 포인트 컷 적용 여부도 결국 프록시가 있어야 실행 시점에 판단할 수 있다. 프록시가 없다면 판단 자체가 불가능. 그런데, 스프링 컨테이너가 프록시를 생성하는 시점은 애플리케이션 로딩 시점이다. → 해당 포인트 컷 지시자가 단독이면 스프링은 모든 빈에 AOP 를 적용하려 한다. 그래서, 이러한 포인트컷 지시자는 최대한 프록시 적용 대상을 축소하는 표현식과 함께 사용해야 한다.
- `@annotation`  : 메서드가 주어진 애노테이션을 가지고 있는 것과 매칭해서 aop 적용
    - ex) @annotation(hello.aop.member.annotation.MethodAop)
- `bean`  : 스프링 빈의 이름으로 AOP 적용여부 지정, 스프링에서만 사용
    - ex) @Around(”bean(orderSerivceImpl) || bean(MemberServiceImpl)”)
- `매개변수 전달` : 포인트컷 표현식을 사용해서 어드바이스에 매개변수를 전달할 수 있다.
    - this, target , @target, @within, @annotation 등 활용 가능
    - this 는 프록시 객체인 빈이, target 은 실제 타겟 인스턴스가 나옴.

    ```java
    @Before("allMember() && args(arg, ..)")
    public void logArgs3(String arg) {
        log.info("args={}", arg); // args=A
    }
    
    @Before("allMember() && this(obj)") 
    public void logArgs4(MemberService obj) {
        log.info("this={}", obj.getClass());
        // this=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
    }
    
    @Before("allMember() && target(obj)")
    public void logArgs5(MemberService obj) {
       log.info("target={}", obj);
       // target=hello.aop.member.MemberServiceImpl@679dd234
    }
    
    @Before("allMember() && @annotation(anno)")
    public void logAnnotation(JoinPoint joinPoint, MethodAop anno) {
    	log.info("@annotation={} annotationValue={}, joinPoint.getSignature(), anno.value());
    }	
    ```

- this vs target
    - this, target 은 다음과 같이 적용 타입 하나를 정확하게 지정해야 한다.( * 사용 불가)

        ```java
        this(hello.aop.member.MemberService)
        target(hello.aop.member.MemberService)
        ```

    - this는 스프링 빈으로 등록되어 있는 ‘프록시 객체’를 대상으로 포인트컷을 매칭
    - target은 실제 target객체를 대상으로 포인트컷을 매칭
    - 프록시를 대상으로하는 this의 경우 프록시 생성 전략이 JDK프록시일 경우, this(MemberServiceImpl)할 경우 포인트컷 매칭에 실패 → 프록시를 대상으로하는 this의 경우 구체클래스를 지정하면 프록시 생성 전략에 따라서 다른 결과가 나올 수 있다.