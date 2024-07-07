# ⭐Docker 명령어
#### **도커 컨테이너에 DB 랑 user_name 과 PW 를 만들어 주고 + 볼륨 마운트 설정을 주면서 mysql 을 띄울 때**
```
docker run --name container -v airdnb_mysql:/var/lib/mysql
-e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=airdnb_db -e MYSQL_USER=team04 -e MYSQL_PASSWORD=1234 -p 3306:3306 -d mysql:8.0.37
```
        
- 도커 컨테이너에 bash shell 로 들어가기
#### **호스트에 있는 파일을 docker로 띄운 MySQL 서버로 파일 전송하기**
```
docker cp /mnt/c/Users/user/[파일이름] [컨테이너 명]:/var/lib/mysql-files/[파일이름]
```
#### **컨테이너 안의 mysql 에 접속하기**
```
docker exec -it [컨테이너 명] mysql -u [사용자이름] -p
```
