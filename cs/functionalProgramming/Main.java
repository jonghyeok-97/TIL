import java.util.Arrays;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        int[] A = {1, 2, 3};
        int[] B = {1, 3};

        SquadSet squadSetA = new SquadSet(A);
        SquadSet squadSetB = new SquadSet(B);

        System.out.println("A 집합 = " + Arrays.toString(A));
        System.out.println("B 집합 = " + Arrays.toString(B));
        System.out.println("합집합sum = " + squadSetA.sum(squadSetB));
        System.out.println("차집합complement = " + squadSetA.complement(squadSetB));
        System.out.println("교집합intersect = " + squadSetA.intersect(squadSetB));

        System.out.println("=============================");

        Map<Integer, Integer> mapA = Map.of(1, 2, 2, 2, 3, 2);
        CountSet countSetA = new CountSet(mapA);

        Map<Integer, Integer> mapB = Map.of(1, 1, 3, 3);
        CountSet countSetB = new CountSet(mapB);

        System.out.println("A Count집합 = " + countSetA.resultAll());
        System.out.println("B Count집합 = " + countSetB.resultAll());

        CountSet append4 = countSetA.append(4);
        System.out.println("A Count집합에 appned 4 = " + append4.resultAll());
        CountSet append1 = countSetA.append(1);
        System.out.println("A Count집합에 appned 1 = " + append1.resultAll());
        CountSet remove1 = countSetB.remove(1);
        System.out.println("B Count집합에 remove 1 = " + remove1.resultAll());
        CountSet remove3 = countSetB.remove(3);
        System.out.println("B Count집합에 remove 3 = " + remove3.resultAll());

        System.out.println("countSetA.sum(countSetB) = " + countSetA.sum(countSetB).resultAll());
        System.out.println("countSetA.complement(countSetB) = " + countSetA.complement(countSetB).resultAll());
        System.out.println("countSetA.intersect(countSetB) = " + countSetA.intersect(countSetB).resultAll());

        countSetB.filter(i -> i > 2) // 1, 3중 3만 남음
                .map(i -> 5) // 3을 5로 바꿈
                .display(i -> System.out.println("i = " + i)); // 5를 출력
    }
}
