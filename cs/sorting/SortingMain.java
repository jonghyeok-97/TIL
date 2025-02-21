public class SortingMain {

    public static void main(String[] args) {
//        Sorting sorting = new BubbleSort();
//        Sorting sorting = new SelectionSort();
        Sorting sorting = new InsertionSort();
        sorting.sort(new int[]{5,7,1,3,2});
    }
}
