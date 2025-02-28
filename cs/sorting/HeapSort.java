public class HeapSort implements Sorting {

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
