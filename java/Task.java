import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task {

    public CompletableFuture<String> doAsyncWithForkJoinPool() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[doAsync] Thread.currentThread().getName() = " + Thread.currentThread().getName());
            return "doAsync";
        });
    }

    public CompletableFuture<String> doAsyncWithExecutorService() {
        ExecutorService es = Executors.newCachedThreadPool();

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[doAsync] Thread.currentThread().getName() = " + Thread.currentThread().getName());
            return "doAsync";
        }, es);
    }
}
