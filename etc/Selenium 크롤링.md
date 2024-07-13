### Selenium 이용한 크롤링
#### **Selenium 은 크롬버전, 크롬드라이버, build.gradle 에 추가할 셀레니움 라이브러리 버전이 호환되어야 합니다.**
* 가상환경에 알맞는 크롬버전을 설치하기 위해 `CPU아키텍처`를 먼저 확인한다.
  * `uname -m` 을 통해 `x86_64` 를 확인
  * 윈도우는 `(Get-WmiObject -Class Win32_ComputerSystem).SystemType` 로 확인
* x86_64 아키텍처에 맞는 Chrome 설치
  * Google Chrome 설치 패키지 다운로드
    * `wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb`  
  * 설치
    * `sudo apt install ./google-chrome-stable_current_amd64.deb`
  * ChromeDriver 다운로드
    * [ChromeDriver 다운로드 페이지](https://googlechromelabs.github.io/chrome-for-testing/#stable) 를 참고하여, Stable 에서 자신의 platform에 맞는 ChromeDriver를 다운로드 합니다.
      * 저는 `OS가 Ubuntu` 이며, `CPU아키텍처가 x86_64` 로 64비트를 사용하고 있기 때문에 Platform 이 `linux64` 인 크롬 버전을 다운받겠습니다.
        * linux64 : 시스템의 OS는 리눅스이며, 64비트 아키텍처(x86_64, amd64) 인 platform을 의미합니다.
      ```angular2html
      wget [크롬버전]
      wget https://storage.googleapis.com/chrome-for-testing-public/126.0.6478.126/linux64/chromedriver-linux64.zip
      ```
    * `unzip chromedriver-linux64.zip` 명령어를 통해 압축파일을 풉니다.
    * 크롬드라이버파일에 실행권한을 주고, 확인합니다.
      ```
      sudo chmod +x chromedriver   // 윈도우는 크롬드라이버파일이 chromedriver.exe 입니다.
      ls -ld chromedriver
      ```
    * 크롬드라이버가 설치됐는지 확인합니다.
      * `chromedriver --version`   // 리눅스 명령어
      * `& 'C:\path\to\chromedriver.exe' --version`  // 윈도우 명령어이며, 'C:\path\to\chromedriver.exe' 대신에 '파일경로'를 작성하세요!

    * 크롬이 설치됐는지 확인합니다.
      * `google-chrome --version`

#### 트러블 슈팅
* 의존성은 공식문서를 참고하자!
  * [셀레니움 공식문서](https://www.selenium.dev/documentation/webdriver/getting_started/)
  * [셀레니움 공식 깃헙의 build.gradle](https://github.com/SeleniumHQ/seleniumhq.github.io/blob/trunk/examples/java/build.gradle)
* 순수 Gradle 로 외부 라이브러리가 포함된 jar 파일을 실행 시키려면 다음과 같은 jar 를 추가해주자.([참고링크](https://colabear754.tistory.com/198))
```angular2html
jar {
    manifest {
        attributes(
                'Main-Class': 'App'
        )
    }
}

jar {
    from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
```
* IDE도움 없이 jar 파일을 시키려면 jar 파일과 크롬드라이버의 경로는 같아야 한다
* jar 로 실행할 경우, 실행되는 디렉토리 경로는 `~~~/build/libs` 이기 때문에, 코드에 상대 경로를 사용했다면 에러가 나니, jar 파일을 옮기도록 하자.