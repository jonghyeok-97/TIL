package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

public class OrderEvent {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        CoffeeOrders coffeeOrders = new CoffeeOrders();
        Cashier cashier = new Cashier(coffeeOrders);
        Manager manager = new Manager(coffeeOrders);

        System.out.println("> 메뉴  =  1. 아메리카노(3s)    2. 카페라떼(5s)    3. 프라프치노(10s)");
        System.out.println("> 주문할 음료를 입력하세요. 예) 아메리카노 2개 => 1:2");

        CompletableFuture<Void> orderTask = CompletableFuture.supplyAsync(() -> {
            while (true) {
                try {
                    // 주문 입력 받기
                    System.out.print("> ");
                    String input = br.readLine();
                    CoffeeOrders orderedCoffees = cashier.takeOrder((input));
                    StringJoiner joiner = new StringJoiner(",", "/", "/");
                    System.out.println(joiner.add(orderedCoffees.getOrderList()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        manager.doWork();
    }
}



