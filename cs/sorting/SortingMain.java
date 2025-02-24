import java.util.Arrays;

public class SortingMain {

    public static void main(String[] args) {
//        Sorting sorting = new BubbleSort();
//        Sorting sorting = new SelectionSort();
//        Sorting sorting = new InsertionSort();
//        Sorting sorting = new MergeSort();
        Sorting sorting = new QuickSort();
        int[] sortedArr = sorting.sort(new int[]{5, 7, 1, 3, 2});
        System.out.println("결과 : " + Arrays.toString(sortedArr));
    }
}
