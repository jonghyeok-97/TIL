import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CountSet {

    private final List<Integer> numbers;

    public CountSet(Map<Integer, Integer> numberMap) {
        this.numbers = toNumberList(numberMap);
    }

    public CountSet append(int element) {
        List<Integer> tempNumberList = new ArrayList<>(numbers);
        tempNumberList.add(element);

        return new CountSet(toNumberMap(tempNumberList));
    }

    public CountSet remove(Integer element) {
        List<Integer> temp = new LinkedList<>(numbers);
        temp.remove(element);

        return new CountSet(toNumberMap(temp));
    }

    public int countFor(int element) {
        return (int) numbers.stream()
                .filter(i -> i == element)
                .count();
    }

    public CountSet sum(CountSet other) {
        List<Integer> temp = new ArrayList<>(numbers);
        List<Integer> otherTemp = new ArrayList<>(other.numbers);

        temp.addAll(otherTemp);

        return new CountSet(toNumberMap(temp));
    }

    public CountSet complement(CountSet other) {
        List<Integer> temp = new ArrayList<>(numbers);
        other.numbers.forEach(temp::remove);

        return new CountSet(toNumberMap(temp));
    }

    public CountSet intersect(CountSet other) {
        List<Integer> collect = numbers.stream()
                .distinct()
                .filter(other.numbers::contains)
                .toList();

        return new CountSet(toNumberMap(collect));
    }

    public Map<Integer, Integer> resultAll() {
        return toNumberMap(numbers);
    }

    public CountSet map(Function<Integer, Integer> mapper) {
        List<Integer> list = numbers.stream()
                .map(mapper)
                .toList();

        return new CountSet(toNumberMap(list));
    }

    public CountSet filter(Predicate<Integer> filter) {
        List<Integer> list = numbers.stream()
                .filter(filter)
                .toList();

        return new CountSet(toNumberMap(list));
    }

    public void display(Consumer<Integer> t) {
        numbers.forEach(t);
    }

    public Map<Integer, Integer> toNumberMap(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.toMap(
                        i -> i,
                        i -> 1,
                        Integer::sum
                ));
    }

    public List<Integer> toNumberList(Map<Integer, Integer> numbersWithCount) {
        List<Integer> temp = new ArrayList<>();
        numbersWithCount.forEach((key, value) ->
                IntStream.range(0, value).forEach(i -> temp.add(key)));

        return temp;
    }
}
