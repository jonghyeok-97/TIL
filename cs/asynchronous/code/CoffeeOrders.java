package code;

import code.CoffeeMenu.Coffee;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

public class CoffeeOrders {

    // thread-safe 큐
    private final Queue<Coffee> coffeQueue = new ConcurrentLinkedQueue<>();

    public CoffeeOrders add(Coffee orderCoffee, int count) {
        IntStream.range(0, count)
                .forEach(i -> {
                    coffeQueue.add(orderCoffee);
                });
        return this;
    }

    public Coffee poll() {
        return coffeQueue.poll();
    }

    public String getOrderList() {
        return coffeQueue.toString();
    }

    public boolean hasCoffeeOrder() {
        return coffeQueue.peek() != null;
    }
}
