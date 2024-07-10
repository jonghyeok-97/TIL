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

#### **config, local, system**
* `global` : 사용자의 홈 디렉토리에 위치한 `~/.gitconfig` 파일에 저장되어 모든 프로젝트에 적용
* `local` : 특정 Git 프로젝트의 `.git/config` 파일에 저장되어 해당 프로젝트에만 적용
* `system` : Git 설치 디렉토리의 `etc/gitconfig` 파일에 저장되어 시스템의 모든 사용자 및 프로젝트에 적용
* `git config (--global or --local or --system) --list` : git 설정된 목록 보기
```angular2html
* gitmessage.txt 적용
git config --global commit.template .gitmessage.txt // 전체 프로젝트에 적용
git config commit.template .gitmessage.txt // 현재 프로젝트에만 적용
```

#### Git 토큰 환경 변수 등록
`export GITHUB_TOKEN=[값]`
