#### ForkJoinPool.commonPool() 의 워커 쓰레드는 데몬쓰레드이다.
```angular2html
// Main 클래스
public static void main(String[] args) throws InterruptedException {
        
        Task task = new Task();
        CompletableFuture<Void> future = task.doAsync().thenAccept(s -> {
            System.out.println("[main] Thread.currentThread().getName() = " + Thread.currentThread().getName());
            System.out.println(s);
        });
        System.out.println("메인 종료");
}
    
// Task 클래스    
public CompletableFuture<String> doAsyncWithForkJoinPool() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[doAsync] Thread.currentThread().getName() = " + Thread.currentThread().getName());
            return "doAsync";
        });
}
    
메인 종료
    
public CompletableFuture<String> doAsyncWithExecutorService() {
        ExecutorService es = Executors.newCachedThreadPool();

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[doAsync] Thread.currentThread().getName() = " + Thread.currentThread().getName());
            return "doAsync";
        }, es);
}
    
메인 종료
[doAsync] Thread.currentThread().getName() = pool-2-thread-1
[main] Thread.currentThread().getName() = pool-2-thread-1
doAsync
```