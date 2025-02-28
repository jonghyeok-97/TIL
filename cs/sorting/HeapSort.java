public class HeapSort implements Sorting {

    /**
     * heap 자료구조를 이용해 정렬하는 알고리즘. heap 자료구조란 complete binary tree 로 마지막 레벨을 제외한 나머지 레벨은 모두 꽉 차있고
     * heap property(부모 노드는 자식보다 크거나 같거나, 자식보다 작거나 같아야 한다) 를 만족하는 자료구조이다.
     *
     * 1. heap 을 만든다. (이를 heapify 라 한다)
     * 2. 루트 노드와 마지막 요소를 바꾸고, 마지막 요소를 제외한 나머지 요소들을 가지고 다시 heap 을 만든다.
     * 3. 1번과 2번을 반복한다.
     *
     * Stable: X
     * In-Place: O
     * 최악,최고,평균 시간복잡도 : O(n log n)
     */
    @Override
    public int[] sortAsc(int[] target) {
        buildMaxHeap(target);
        sortHeap(target);
        return target;
    }

    private void sortHeap(int[] target) {
        for (int i = target.length - 1; i > 0; i--) {
            swap(target, i, 0);
            maxHeapify(target, i, 0);
        }
    }

    private void buildMaxHeap(int[] target) {
        int length = target.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            maxHeapify(target, length, i);
        }
    }

    private void maxHeapify(int[] target, int n, int i) {
        int root = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && target[root] < target[left]) {
            root = left;
        }

        if (right < n && target[root] < target[right]) {
            root = right;
        }

        if (root != i) {
            swap(target, i, root);
            maxHeapify(target, n, root);
        }
    }

    private void swap(int[] target, int root, int i) {
        int temp = target[root];
        target[root] = target[i];
        target[i] = temp;
    }
}
