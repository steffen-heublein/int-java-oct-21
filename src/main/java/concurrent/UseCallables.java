package concurrent;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

class MyJob implements Callable<String> {
    private static int nextId = 1;
    private String name = "Job " + nextId++;

    private static void randomDelay() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
    }

    @Override
    public String call() throws Exception {
        System.out.println(name + " starting");
        randomDelay();
        if (ThreadLocalRandom.current().nextInt(0, 6) > 4) {
            throw new SQLException("DB Broke");
        }
        System.out.println(name + " finishing");
        return name + " result";
    }
}

public class UseCallables {
    public static void main(String[] args) {
        int JOB_COUNT = 6;
        ExecutorService es = Executors.newFixedThreadPool(2);
        List<Future<String>> lfs = new LinkedList<>();
        for (int i = 0; i < JOB_COUNT; i++) {
            lfs.add(es.submit(new MyJob()));
        }
        es.shutdown();
        while (lfs.size() > 0) {
            Iterator<Future<String>> ifs = lfs.listIterator();
            while (ifs.hasNext()) {
                Future<String> fs = ifs.next();
                if (fs.isDone()) {
                    try {
                        System.out.println("a job returned: " + fs.get());
                    } catch (InterruptedException e) {
                        System.out.println("get was interrupted");
                    } catch (ExecutionException e) {
                        System.out.println("a job threw an exception: " + e.getCause().getClass()
                                + ", message " + e.getCause().getMessage());
                    }
                    ifs.remove();
                }
            }
        }
    }
}
