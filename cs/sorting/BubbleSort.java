import java.util.Arrays;

public class BubbleSort implements Sorting {

    /**
     * 두 원소의 대소를 비교해가며 정렬한다.
     * 시간복잡도: O(n2)
     * In-PLACE : O
     * Stable : O
     */
    @Override
    public int[] sort(int[] target) {
        for (int i = 0; i < target.length; i++) {
            System.out.println(i+"회전 결과 : " + Arrays.toString(target));
            for (int j = 0; j < target.length - 1; j++) {
                int here = target[j];
                int next = target[j + 1];
                if (here > next) {
                    target[j] = next;
                    target[j + 1] = here;
                }
            }
        }
        return target;
    }
}
