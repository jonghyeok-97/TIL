# ⭐OAuth & Security 삽질 일기
> OAuth2.0 을 사용하는 방법으로 Security 를 이용한 방법과 이용하지 않는 방법이 있다.

#### Security 로 OAuth 이용하는 방법은
* `implementation 'org.springframework.boot:spring-boot-starter-oauth2-client` 를 build.gradle 에 넣고
    * 인텔리제이 External Libraries 에 Security 의존성이 생긴다.
    * Security 를 이용한 OAuth 를 구현하려면 이 의존성이 필요하다.
* `@Configuration` 어노테이션이 달린 Config 를 만들어서 인가가 필요한 요청, 인가가 성공한 후 요청, 인가 후 처리하는 서비스까지 구현하면 된다
* `@Configuration` 어노테이션이 있는 Config 덕에 우리는 Controller 를 따로 구현하지 않아도 된다.
* Security 를 사용할 때에는 `localhost:8080/login` 를 브라우저에 입력만 해도 인가서버에 해당하는 로그인 페이지로 리다이렉트 된다.

#### Security 없이 OAuth 를 구현할 때는
* 꼭 꼭 Security 때 넣었던 `starter-oauth2-client` 의존성을 없애야 한다.
  * Security 를 사용하지 않기 때문에 `Callback url` 에 해당하는 요청을 Controller 에서 받아야 하는데,
  * 없애지 않으면 Controller 가 요청을 받지 못한다..(고통스런 2일..)
  * `starter-oauth2-client` 의존성을 없애면 External Libraries 에 Security 관련 라이브러리가 사라지며, Security 없이 OAuth 를 구현하는 것은 어떤 의존성도 추가하지 않아도 된다.
* Security 를 사용하지 않기 때문에 Controller 를 직접 구현하면 된다.
* Security 를 사용하지 않기 때문에 `localhost:8080/login` 를 이용하면 404 white Label 을 볼 수 있을 것이니
* 만약, OAuth 를 이용해서 로그인 후, (인가 서버의 code 를 받을 때) 무한 리다이렉션이 생긴다면,
  * OAuth 를 제공해주는 서버에 애플리케이션을 등록할 때 적은 콜백url 이랑 애플리케이션 내의 code 를 받을 callback url 이랑 같은지 확인하자
---  

#### Security 의 Config 일부 학습
* `@EnableWebSecurity`
  * 스프링 시큐리티 필터가 스프링 필터체인에 등록되는 어노테이션
* `void configure(HttpSecurity http)` 를 오버라이드 하기
* `@EnableGloblaMethodSecurity(securedEnabled = true)`
  * 컨트롤러 메서드에 개별적으로 @Secured 활성화 가능
* `http.csrf.disabled()` : CSRF 공격으로부터 보안을 해제
  * 권장되지 않음, 시큐리티는 CSRF 로부터 공격을 디폴트로 보호해주고 있다. 
  * 그럼에도 활성화 하지 않는 이유
    * CSRF 토큰을 검증하는 과정이 번거로움
    * 특히, 프론트엔드와 백엔드를 별도로 개발하거나 API를 사용하는 경우에는 CSRF 토큰을 관리하는 것이 더 어려움.
* login/logout 페이지는 `WebSecurityConfigurerAdapter` 를 상속받지 않은 Config 를 추가하지 않으면 Security 가 자체적으로 만든 login/logout 창으로 이동게 된다.
* 스프링Security 는 로그인되면 세션안에 시큐리티 세션이라고 별도의 세션을 하나 만들어 준다.
  * Security Session 안에 Authentication 안에 UserDetails 가 만들어진다.
  * OAuth2 로 로그인 한 경우 OAuth2User 로 만들어 진다

아래 링크를 보며 연습 및 삽질을 하게 되었습니다.
이러한 과정에 대한 내용이 많은 구글 블로그에는 없어서 삽질을 통해 얻은 것들이니 아래 블로그를 보기 전에 참고하면 보면 좋을 것 같다. 

#### OAuth 전체적인 개념 설명
* 블로그 : [https://velog.io/@shyuuuuni/OAuth-알아보기-with-github-로그인](https://velog.io/@shyuuuuni/OAuth-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0-with-github-%EB%A1%9C%EA%B7%B8%EC%9D%B8)
- 강의 : https://www.inflearn.com/course/lecture?courseSlug=web2-oauth2&unitId=36285(생활코딩)
#### OAuth2.0 을 Security 와 함께 구현하기
* 강의 : [https://www.inflearn.com/course/lecture?courseSlug=스프링부트-시큐리티&unitId=97765](https://www.inflearn.com/course/lecture?courseSlug=%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0&unitId=97765)
* 위 강의를 보며 누군가가 따라친 코드 : https://github.com/freemoon99/study/blob/main/practie_springSecurity/loginWithGithubOauth2/src/main/java/com/example/UserServer/config/SecurityConfig.java
https://kakao-tam.tistory.com/54
#### OAuth2.0 을 Security 없이 구현하기
* 참고블로그
  * https://velog.io/@bongf/study-OAuth
  * https://withseungryu.tistory.com/110
#### OAuth2.0 + Security + JWT 토큰
* 코드 : https://velog.io/@yshjft/Spring-Security-OAuth2.0