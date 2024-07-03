# ⭐Docker 명령어
#### **호스트에 있는 파일을 docker로 띄운 MySQL 서버로 파일 전송하기**
```
docker cp /mnt/c/Users/user/[파일이름] [컨테이너 명]:/var/lib/mysql-files/[파일이름]
```
#### **컨테이너 안의 mysql 에 접속하기**
```
docker exec -it [컨테이너 명] mysql -u [사용자이름] -p
```