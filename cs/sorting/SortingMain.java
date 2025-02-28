import java.util.Arrays;

public class SortingMain {

    public static void main(String[] args) {
//        Sorting sorting = new BubbleSort();
//        Sorting sorting = new SelectionSort();
//        Sorting sorting = new InsertionSort();
//        Sorting sorting = new MergeSort();
//        Sorting sorting = new QuickSort();
        Sorting sorting = new HeapSort();
        int[] sortedArr = sorting.sort(new int[]{230, 10, 60, 550, 40, 220, 20});
        System.out.println("결과 : " + Arrays.toString(sortedArr));
    }
}
