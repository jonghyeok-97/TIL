#### 아래 코드의 sout 의 결과가 무엇이 나올까?
```
Long Long_id = null;
long long_id = 3L;
System.out.println(Long_id == long_id);
```
* 정답은 **`NPE`** 가 나온다.

#### 왜 NPE가 나올까?
자바는 오토 언박싱, 오토 박싱 기능을 지원한다.
그래서, Long타입을 long 을 비교하면 오토 언박싱을 이용해 참조타입을 원시타입으로 바꿔준다.

그래서, Long 타입 값에 숫자가 들어가면 `==` 비교에는 true 가 나온다.
```
Long Long_id = 3L;
long long_id = 3L;
System.out.println(Long_Id == long_id);
```

하지만, Long 객체에 null 이 들어간다면!?
인텔리제이는 Long 을 자동 언박싱 하는 과정에서 NPE가 나올 것이라 경고한다!
```
Long Long_id = null;
long long_id = 3L;
System.out.println(Long_Id == long_id);
```

그 이유는, Long 을 long 으로 오토 언박싱 할 떄, 자바는 `Long타입.longValue()` 를 사용하고,
이과정에서 Long 이 null 이라면, null.longValue() 가 되기 때문에 NPE 가 발생한다.

>> 더 나아가, 래퍼타입은 객체로 취급되기 때문에 null 을 포함할 수가 있다.
Long 타입과 long 타입의 일관성을 유지하는 것도 중요하다.
코틀린에서는 숫자를 래퍼타입으로 취급하고 있다.

그러니, 아래와 같은 코드를 일관성있게, NPE가 발생하지 않도록 수정하자.
```angular2html
private Long milestone_id;

...(중략)

public boolean hasMilestoneId(long milestoneId) {
    return this.milestone_id == milestoneId;
  }
```

```angular2html
private Long milestone_id;

...(중략)

public boolean hasMilestoneId(Long milestoneId) {
    if(this.milestone_id == null) {
        return false;
    }
    return this.milestone_id.equals(milestoneId);
  }
```