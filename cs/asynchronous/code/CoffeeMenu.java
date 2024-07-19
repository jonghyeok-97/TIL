package code;

import java.util.Map;

public class CoffeeMenu {

    private final Map<Integer, Coffee> menus = Map.of(
            1, Coffee.아메리카노,
            2, Coffee.카페라떼,
            3, Coffee.프라푸치노
    );

    public Coffee getCoffee(int index) {
        return menus.get(index);
    }

    public enum Coffee {
        아메리카노(1),
        카페라떼(3),
        프라푸치노(10);

        private final int second;

        Coffee(int second) {
            this.second = second;
        }

        public int getSecond() {
            return second * 1000;
        }
    }
}
