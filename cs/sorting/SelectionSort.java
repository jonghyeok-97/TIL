import java.lang.reflect.Array;
import java.util.Arrays;

public class SelectionSort implements Sorting {

    /**
     * 원소의 위치는 이미 정해져있고, 어떤 원소를 넣을지 선택하는 정렬 알고리즘
     * 버블 정렬만큼 비교 횟수가 많지만, 정렬이 되어있다면 교환 횟수는 버블 정렬보다 적다.
     * 시간 복잡도: O(n2)
     * Stable : X (오름 차순이냐, 내림차순이냐에 따라 달라짐)
     * In-Place : O
     * <p>
     * 해당 자리를 선택하고 그 자리에 오는 값을 찾는 것.
     */
    @Override
    public int[] sortAsc(int[] target) {
        for (int i = 0; i < target.length; i++) {
            System.out.println(i+"회차 : " + Arrays.toString(target));
            int value = Integer.MAX_VALUE;
            int index = 0;
            for (int j = i; j < target.length; j++) {
                if (target[j] < value) {
                    index = j;
                    value = target[j];
                }
            }
            target[index] = target[i];
            target[i] = value;
        }

        return target;
    }
}
