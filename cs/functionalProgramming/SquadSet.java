import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SquadSet {

    private final List<Integer> numbers;

    public SquadSet(int[] numbers) {
        this.numbers = new ArrayList<>();
        for (int number : numbers) {
            this.numbers.add(number);
        }
    }

    public List<Integer> sum(SquadSet other) {
        List<Integer> tempNumbers = new ArrayList<>(numbers);
        tempNumbers.addAll(other.numbers);
        return tempNumbers.stream()
                .distinct()
                .toList();
    }

    public List<Integer> complement(SquadSet other) {
        return numbers.stream()
                .filter(number -> !other.numbers.contains(number))
                .toList();
    }

    public List<Integer>
    intersect(SquadSet other) {
        return numbers.stream()
                .filter(number -> other.numbers.contains(number))
                .toList();
    }

    public int[] resultAll() {
        return IntStream.range(0, numbers.size())
                .toArray();
    }
}
