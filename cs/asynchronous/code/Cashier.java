package code;

import code.CoffeeMenu.Coffee;
import java.util.List;
import java.util.stream.Stream;

public class Cashier {

    private final CoffeeMenu coffeeMenu;
    private final CoffeeOrders coffeeOrders;

    public Cashier(CoffeeOrders coffeeOrders) {
        this.coffeeMenu = new CoffeeMenu();
        this.coffeeOrders = coffeeOrders;
    }

    public CoffeeOrders takeOrder(String input) {
        List<Integer> splitedOrderByColon = Stream.of(input.split(":"))
                .map(Integer::parseInt)
                .toList();

        Coffee coffee = coffeeMenu.getCoffee(splitedOrderByColon.get(0));
        int menuCount = splitedOrderByColon.get(1);

        return coffeeOrders.add(coffee, menuCount);
    }
}
