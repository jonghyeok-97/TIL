package code;

import code.CoffeeMenu.Coffee;

public class Barista {

    public void makeCoffee(Coffee coffee) {
        try {
            System.out.println(coffee + " 시작");
            Thread.sleep(coffee.getSecond());
            System.out.println(coffee + " 완성");
        } catch (InterruptedException ignored) {}
    }
}
