#### heap sort 
- 최선/최악 시간 복잡도 O(n logn)
- In-Place : O
- Stable : X
- 이진 힙(binary heap) 자료구조를 사용

* Heap 이란?
- complete binary tree 이면서
  - full binary tree: 모든 레벨에 노드들이 꽉 차 있는 형태(리프 노드 포함)
  - complete binary tree: 마지막 레벨을 제외하면 완전히 꽉 차있고, 마지막 레벨에는 가장 오른쪽부터 연속된 몇 개의 노드가 비어있을 수 있음.
- heap property 를 만족해야 한다.
  - max heap property: 부모는 자식보다 크거나 같다.
  - min heap property: 부모는 자식보다 작거나 같다.

- Heap(힙)은 일차원 배열로 표현이 가능하다.
  - 루트 노드 A[1]
  - A[i]의 부모 = A[i/2]
  - A[i]의 왼쪽 자식 = A[2i]
  - A[i]의 오른쪽 자식 = A[2i+1]

- Max Heapify 과정
  - Heapify? 특정 노드의 서브트리가 힙의 성질을 만족하도록 재구성하는 과정 
  - 특정 노드(i) 기준으로 왼쪽 서브트리와 오른쪽 서브트리가 힙을 만족할 때 시작
  - 특정 노드인 i를 루트로 삼고, i를 포함한 트리가 힙이 되도록 재귀적으로 정렬하는 과정을 heapify라 함.
  ```angular2html
  Max-HEAPIFY (A, i) {
    if there is no child of A[i]
        return;
    k <- index of the biggest child of i;
    if(A[i] >= A[k]
        return;
    exchange A[i] and A[k]
    Max-HEAPIFY(A, k);
  }
  ```

* Heap sort
1. 정렬할 배열을 힙으로 만들기.
   - 일차원 배열을 complete binary tree 로 본 후, heap property 를 만족하도록 정렬하자.
   - 배열의 인덱스를 역순으로 읽어서 리프노드가 아니고, 서브트리를 가진 노드부터 Heapify 를 진행
     - 배열의 크기가 n 이라면 n/2 노드부터 진행하게 됨(complete binary tree 의 특징)
   - 이 과정이 O(n) 의 시간을 갖는다
2. 힙에서 최대값(루트)을 가장 마지막 값과 바꾼다.
3. 힙의 크기가 1 줄어든 것으로 간주한다. 즉, 가장 마지막 값은 힙의 일부가 아닌 것으로 간주
4. 루트 노드에 대해서 HEAPIFY(1) 한다.
5. 2~4번을 반복(n log(n)한다.
```angular2html
HEAPSORT(A)
1. BUILD-MAX-HEAP(A)                : O(n)
2. for(i <- heap_size downto 2 do)  : n-1 times
3.      exchange A[1] .. A[i]       : O(1)
4.      heap_size <- heap_size - 1  : O(1)
5.      MAX-HEAPIFY(A, 1)           : O(log n)

TOTAL time: O(n log n)
```