import java.util.Arrays;

public class InsertionSort implements Sorting {

    /**
     * 정렬된 배열에 현재 요소를 삽입
     * selectionSort에서 배열은 정렬되지 않음.
     * 최선 시간복잡도 - O(n) (만약, 배열의 모든 요소가 정렬되어있을 경우, 삽입할 요소를 비교만 한다)
     * 최악 시간복잡도 - O(n2) (삽입할 요소를 비교하고, 정렬하면서 다른 요소를 뒤로 한칸식 이동하기 때문)
     *
     * In-Place : O
     * Stable : O
     */
    @Override
    public int[] sortAsc(int[] target) {
        for (int i = 1; i < target.length; i++) {
            int here = target[i];
            int prevIndex = i - 1;
            while (prevIndex >= 0 && target[prevIndex] > here) {
                target[prevIndex + 1] = target[prevIndex];
                prevIndex--;
            }
            target[prevIndex+1] = here;
            System.out.println(i + "회전 결과 : " + Arrays.toString(target));
        }
        return target;
    }
}
