import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class CompletableEx1 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        Task task = new Task();
        CompletableFuture<Void> future = task.doAsyncWithExecutorService().thenAccept(s -> {
            System.out.println("[main] Thread.currentThread().getName() = " + Thread.currentThread().getName());
            System.out.println(s);
        });
        System.out.println("메인 종료");
    }

}
