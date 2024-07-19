package code;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Manager {

    private static final int MAX_CONCURRENT_TASKS = 2;

    private final AtomicInteger currentTasks = new AtomicInteger(0);
    private final Barista barista;
    private final CoffeeOrders coffeeOrders;
    private boolean isOrderStart;

    public Manager(CoffeeOrders coffeeOrders) {
        this.barista = new Barista();
        this.coffeeOrders = coffeeOrders;
    }

    public void doWork() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (coffeeOrders.hasCoffeeOrder() && currentTasks.get() < MAX_CONCURRENT_TASKS) {
                    isOrderStart = true;
                    currentTasks.incrementAndGet();
                    CompletableFuture<Void> makingCoffeeTask = CompletableFuture.runAsync(() -> {
                        barista.makeCoffee(coffeeOrders.poll());
                    }).thenRun(currentTasks::decrementAndGet);
                }
                if (isOrderStart && !coffeeOrders.hasCoffeeOrder() && currentTasks.get() == 0) {
                    try {
                        Thread.sleep(3000);
                        if (!coffeeOrders.hasCoffeeOrder() && currentTasks.get() == 0) {
                            timer.cancel();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
}
