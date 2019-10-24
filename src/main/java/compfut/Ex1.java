package compfut;

import java.util.concurrent.CompletableFuture;

public class Ex1 {
    public static void main(String[] args) {
        CompletableFuture<Void> cfs = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " supplying...");
            return "data.txt";
        })
                .thenApplyAsync(x -> x.toUpperCase())
                .thenApplyAsync(x -> {
                    if (Math.random() > 0.5) throw new RuntimeException("It Broke");
                    else return x;
                })
                .thenApplyAsync(x -> "Dear file " + x)
                .handleAsync((v, e) -> {
                    if (v != null) return "Successful value " + v;
                    else return "Recovered from " + e.getMessage();
                })
                .thenAcceptAsync(x -> System.out.println(Thread.currentThread().getName() + " value is  " + x));
        System.out.println("pipeline created...");
        cfs.join();
        System.out.println("main exiting....");
    }
}
