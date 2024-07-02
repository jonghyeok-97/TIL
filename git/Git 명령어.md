# ⭐Git 명령어
#### **origin의 main 을 참고해서 local 의main 브랜치를 새롭게 만들고 싶을 때**
1. main 브랜치로 와서 브랜치 명을 잠시 바꾼다.
2. origin의 main 을 당겨온다.
```
git branch -m [새로운 브랜치 이름]
git checkout -b main origin/main
```
#### **원격 저장소 URL 변경하기**
`git remote set-url origin [저장소URL]`
#### **삭제된 브랜치 복원하기**
1. 어떤 커밋에서 브랜치가 삭제됐는지 해당 커밋ID찾기
2. 찾은 커밋 ID를 사용해서 브랜치 복원하기
```
git reflog
git checkout -b [브랜치 명] [커밋ID] // ex) git checkout -b feature abc1234
```
#### **Merge 충돌 날 때**
* 충돌을 해결하지 않고 병합 취소할 때
  `git merge --abort`
* 충돌을 해결하고 충돌 해결 커밋을 생성할 때
  `git merge --continue`
* 병합을 중단하지만, 병합을 시도하기 전의 상태로 되돌리지 않음
  `git merge --quit`