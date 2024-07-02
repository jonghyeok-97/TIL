#### Swap memory ?
메모리(RAM)이 부족할 때, 디스크의 일부를 메모리로 사용하는 것.
이를 통해 메모리가 부족한 상황에서도 추가적인 메모리 공간 확보를 할 수 있음.
#### Why use Swap memory in Ec2.micro ?
Ec2.micro 와 같은 저사양 서버는 메모리가 제한적이기 때문에, boot 같이 무거운 프로세스를 실행하는 것으로도 메모리가 부족해서 멈출 수 있다.(메모리가 부족할 때, 멈춰지는 프로세스는 무거운 순이다)
#### 명령어
```
sudo dd if=/dev/zero of=/swapfile bs=128M count=16 
    * dd 명령어로 swapfile(빈 파일) 생성
    * if=/dev/zero -> 입력 파일을 /dev/zero 로 지정해서 모든 바이트를 0으로 채움
    * of=/swapfile -> 출력 파일을 /swapfile 로 지정
    * bs=128M -> 블록 크기를 128MB 로 설정
    * count=16 -> 128MB 크기의 블록을 16번 반복해서 2GB 의 파일을 만듬
sudo chmod 600 /swapfile // 파일 소유자에게만 읽기, 쓰기 권한 부여
sudo mkswap /swapfile
    * /swapfile 을 Swap 공간으로 초기화
    * mkswap : Make Swap 의 약자로 스왑파티션 or 스왑파일을 생성하는 명령어
sudo swapon /swapfile
    * /swapfile 을 활성화하여 Swap 공간으로 사용 시작
    * swapon : 스왑으로 사용하는 파일의 정보를 보여주는 명령어
sudo swapon -s  // 현재 활성화된 Swap 공간의 상태를 확인, 정보를 출력
sudo vi /etc/fstab  // /etc/fstab 파일을 편집하여 시스템 부팅 시 자동으로 Swap 공간을 마운트하도록 설정
/swapfile swap swap defaults 0 0
    * /etc/fstab 에 추가할 항목으로, Swap 파일을 시스템 부팅 시 자동으로 활성화 하도록 함
    * swap : 파일 시스템 타입
    * swap : 마운트 옵션
    * defaults : 기본 마운트 옵션
    * 0 0 : 덤프 및 fsck 설