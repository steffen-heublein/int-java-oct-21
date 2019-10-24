package compfut;

import java.util.concurrent.CompletableFuture;

public class Callback {

    public static CompletableFuture<String> getFileContents(String filename) {
        CompletableFuture<String> rv = new CompletableFuture<>();
        new Thread(
                () -> {
                    System.out.println("Background processing starting...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Background job completing...");
                    rv.complete("This is the contents of the file " + filename);
                }
        ).start();
        return rv;
    }

    public static void main(String[] args) {
        CompletableFuture<Void> cfv = CompletableFuture.supplyAsync(() -> "data.txt")
                .thenCompose(Callback::getFileContents)
                .thenAcceptAsync(System.out::println);
        System.out.println("Pipeline assembled");
        cfv.join();
        System.out.println("Finished...");
    }
}
