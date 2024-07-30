# http 클라이언트

### HTTPS ?
SSL 이나 TLS 로 클라이언트-서버 간의 커뮤니케이션을 암호화하여 통신하는 방법
현재 SSL(Secure Sockets Layer, 보안 소켓 계층)은 TLS(Transport Layer Security)로 대체됨.
TLS 는 이메일, 웹 브라우징, 메시징 등 다른 프로토콜들의 감청을 통한 정보의 변형을 감지함.


### Web Socket vs Socket
- Web Socket : TCP/IP 위에서 HTTP 프로토콜으로 애플리케이션 서버에 연결하고, 브라우저에서 동작.
- Socket : TCP/IP 위에서 HTTP 프로토콜이나, 브라우저에 국한되지 않고 다른 프로토콜을 사용해서 연결.
- 참고 - [Difference between socket and web socket?](https://stackoverflow.com/questions/4973622/difference-between-socket-and-websocket)

### WireShark
* 설치 및 다운로드 참고 블로그 : [링크](https://luckygg.tistory.com/379)
* 사용방법 참고 유튜브 : [링크](https://www.youtube.com/watch?v=qTaOZrDnMzQ)

#### 유튜브에 나오는 와이어샤크 관련 용어
* 드릴 다운 : 상위 수준의 데이터에서 하위 수준의 데이터로 탐색해 나가는 것
* PCAP : Packet Capture 로 네트워크 트래픽을 캡처하는 도구로 이 중 하나가 와이어샤크
* 이더넷 인테페이스 : 유선 네트워크
* 델타 타임 : 특정 패킷과 이전 패킷 사이의 시간 차이를 나타내는 측정값
* 보고싶지 않은 패킷 보고싶지 않을 때
  * ex) ARP, STP, CDP 같은 프로토콜 보고 싶지 않다? -> 필터에 `!(arp or stp or cdp)` 작성
* 3-way 핸드셰이크 시작을 보고싶을 때
  * ex) tcp.flags.syn==1
* 다양한 TCP 분석 플래그를 기반으로 패킷을 필터링하는 데 사용
  * tcp.analysis.flags
* TCP패킷 중 비정상적으로 종료된 패킷 볼 떄
  * tcp.flags.reset==1

* Main.java 를 실행했을 때의 와이어샤크로 본 패킷들
![스크린샷 2024-07-30 151232.png](..%2F..%2F..%2F..%2FDesktop%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-07-30%20151232.png)

### 클라이언트의(브라우저)의 기능
* Http Response 메시지 해석
* Http Request 메시지 만듬
  * Request Line
    * Http Method
    * URL
    * Http 프로토콜 버전
  * Header
    * Host : 서버의 도메인 주소를 담아서 줌
    * Content-Type : 서버에게 보내는 미디어 타입(데이터 형태)를 알려줌
    * Content-Length : Body 에 데이터를 담아서 보낼 때, 
    * Accept : Http Response 를 줄 때, 어떤 데이터만을 달라고 서버에게 요청
    * 캐시
      * Cache-Control : `max-age=3600` 처럼 만료 시간을 줌
      * Last-Modified : 2020년 11월 (UTC 표기법으로 적어야 함)
  * Body
    * byte[] 배열에 담아서 줌
* 쿠키 저장
* DNS에 접근하여 도메인에 해당하는 IP주소 얻음
* 찾은 IP주소로 Http 프로토콜을 사용해서 서버에게 메시지 보냄 

### 서비스가 느리다?
- gzip(압축) 활용
- Http Version 2 활용
- Cache-Control 활용 -> 안변하는 데이터에 활용

### 개념
- 인터넷
  - 전세계의 컴퓨터를 연결해서 **데이터를 주고 받을 수 있는** 인프라, **TCP/IP**로 동작
  - 데이터를 주고 받는 방법은 웹의 브라우저도 있고, SMTP 기반의 메일도 있고 등등
- 웹 : HyperText 문서를 제공/조회 하는 서비스. 인터넷의 일부분. HTTP 기반으로 동작. 
- HTTP : 웹에서 클라이언트와 서버간의 메시지를 주고받는 규칙을 정의한 것
- REST(Representation State Transfer) : 웹 아키텍처 스타일 중 하나이며 사실상 웹 아키텍처의 표준, **URI 로 자원을 식별하고 HTTP 메서드를 잘 정의해서 자원을 조작하기**
- RESTful : 모든 것이 자원(URI를 통한 고유한 자원 식별 가능), 자원은 HTML 과 JSON 등 다양한 형태로 표현, stateless, 캐시 가능

CORS 를 공부하려면 SOP 를 공부해야한다.