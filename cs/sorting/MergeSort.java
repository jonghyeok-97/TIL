import java.util.Arrays;

public class MergeSort implements Sorting {

    /**
     * 분할 후, 합치면서 정렬. 합칠 때, 추가 배열을 쓴다는 점에서 In-Place : X. 분할 시, 가운데를 기준으로 배열의 길이가 1일 때까지 나눈다.
     * 순차적으로 정렬을 하기 때문에 LinkedList 정렬에 효과적이다.
     *
     * 최악,최선,평균 시간복잡도는 O(n logn) 이다.
     * In-Place : X
     * Stable : O
     */
    @Override
    public int[] sortAsc(int[] target) {
        System.out.println("원본 : " + Arrays.toString(target));
        divide(target, 0, target.length - 1);
        System.out.println("결과 : " + Arrays.toString(target));
        return target;
    }

    @Override
    public int[] sortDecs(int[] target) {
        return new int[0];
    }

    private void divide(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        divide(arr, left, mid);
        divide(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private void merge(int[] arr, int left, int mid, int right) {

        int[] l = Arrays.copyOfRange(arr, left, mid + 1);
        int[] r = Arrays.copyOfRange(arr, mid + 1, right + 1);
        System.out.println("왼쪽 : " + Arrays.toString(l) + " 오른쪽 : " + Arrays.toString(r));

        int lIdx = 0;
        int rIdx = 0;
        int k = left;

        while ((l.length > lIdx && r.length > rIdx)) {
            if (l[lIdx] > r[rIdx]) {
                arr[k] = r[rIdx++];
            } else {
                arr[k] = l[lIdx++];
            }
            k++;
        }
        while (l.length > lIdx) {
            arr[k++] = l[lIdx++];
        }
        while (r.length > rIdx) {
            arr[k++] = r[rIdx++];
        }
    }
}
