public class QuickSort implements Sorting {

    /**]
     * 피벗을 기준으로 배열을 재귀적으로 분할하며 정렬. 새로운 배열을 쓰지 않다보니 다른 O(n log n) 에 비해 캐시 친화적이라 빠르다. 병합 할 때는 그냥 병합
     *
     * 최선 시간복잡도 : O(n logn) -> 다른 O(n logn) 을 가진 알고리즘보다 빠른 이유는 새 배열을 사용하지 않기 때문에 캐시 친화적이다.
     * 최악 시간 복잡도 : O(n2) -> 피벗이 배열의 최소, 최대값이라서 파티션이 제대로 이뤄지지 않을 경우. 파티션 나누는 횟수 n - 1, 파티션 안에서 정렬하기 위해 n
     * Stable -> X [8, 4, 8] 에서 어떤 8이 피벗으로 선택되냐에 따라 순서가 바뀐다.
     * In-Place -> O
     */
    @Override
    public int[] sortAsc(int[] target) {
        partition(target, 0, target.length - 1);
        return target;
    }

    @Override
    public int[] sortDecs(int[] target) {
        return new int[0];
    }

    private void partition(int[] arr, int left, int right) {
        if(left >= right) return;
        int mid = (left + right) / 2;

        // 피벗은 배열의 최대, 최소값이 아니어서 O(n2) 예방.
        int pivot = arr[mid];
        if (arr[left] == pivot || arr[right] == pivot) {
            pivot = arr[left + 1];
        }

        int i = left, j = right;

        while (i < j) {
            while (pivot < arr[j]) {
                j--;
            }
            while (pivot > arr[i] && i < j) {
                i++;
            }
            swap(arr, i, j);
        }
        // 피벗을 제외하고 다시 파티션
        partition(arr, left, i - 1);
        partition(arr, i + 1, right);
    }

    private void swap(int[] arr, int idx1, int idx2) {
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }
}
