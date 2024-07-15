## Process Memory Model

----
### Stack
#### `Stack Frame`
  * 각 함수 호출마다 생성되는 메모리 블록으로 함수의 지역 변수, 매개변수, **반환 주소**가 있다.
#### `Call Stack`
  * 여러 개의 code.Stack Frame 으로 구성됨.
#### `Stack Pointer`
  * Call Stack의 최상단을 가리키는 CPU 레지스터로, 이를 가지고 code.Stack Frame 을 관리한다.
#### `Pointer`
  * Stack Frame 에 저장되어있는 변수로 특정 메모리 주소를 저장하고 있다. 이 주소는 스택, 힙, 전역 등 다양한 메모리 영역에 위치할 수 있다.

