#### **네트워크 인터페이스 확인**
```angular2html
윈도우 : ipconfig
유닉스/리눅스 : ifconfig
```

#### **네트워크 연결 상태 확인하기**
* `ping [호스트명 or IP주소]`

#### **호스트가 해당 포트를 쓰고 있는지 확인하기**
* 22번 포트 사용 여부 확인
  * 22번 포트를 사용하고 있는 PID가 나온다
    * 윈도우 : `netstat -ano | findstr :22`
      ![image](https://github.com/jonghyeok-97/TIL/assets/136168660/58416382-23df-4c97-b56a-45ea3bb453b8)
    * 리눅스 : `netstat -tuln | grep :22`

#### **특정 PID(Process Identifier)의 프로세스 정보 확인**
* PID가 3524인 프로세스의 정보 확인하기
  * 윈도우 : `tasklist /FI "PID eq 3524"`
    ![스크린샷 2024-07-08 231928](https://github.com/jonghyeok-97/TIL/assets/136168660/6d12a059-b120-4218-8ee3-9098786bd4f0)
  * 리눅스 : `ps -p 3524 -f`

#### **파일 및 디렉터리 권한**
* 소유자(User), 그룹(Group), 기타(Others)로 나뉨
```angular2html
ls -ld [파일 or 디렉터리] : 파일 or 디렉터리의 권한 확인
d : 디렉토리
r : 읽기(4)
w : 쓰기(2)
x : 실행권한(1)
- : 권한없음(0)
```
* ex) 특정 파일, 디렉토리가 rwxr-xr-x 면   
  * 소유자는 읽기,쓰기,실행권한 있음
  * 그룹은 읽기, 실행권한 있음
  * 기타는 읽기, 실행권한 있음

#### **root 와 home**
* `/` : root 를 의미
  * `mkdir /cs10` : root 에 cs10 디렉토리 생성
* `~` : home 을 의미
  * `mkdir cs10` : 현재디렉토리에서 cs10 디렉토리 생성

#### 가상환경에서 특정 컴퓨터로 파일 전송하기
* `scp -r ~/cs10/linuxServer/selenium user@[IP주소]:/C/Users/[사용자이름]/Desktop/`
  * 포트 추가하기 `-P 2222`

#### 환경변수 설정하기
```angular2html
nano ~/.bashrc       // bash셸 열기, zsh는 ~/.zshrc
export 값="파일경로"
source ~/.bashrc     // 적용하기
```

#### 파일 및 디렉토리 복사
```angular2html
cp -r [디렉터리 있는 경로] [옮길 경로]  // 디렉토리는 하위 파일도 복사해야 하기 때문에 -r 옵션(recursive)을 추가
cp [파일 있는 경로] [옮길 경로]     // 파일복사
```