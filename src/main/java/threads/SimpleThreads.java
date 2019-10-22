package threads;

class MyJob implements Runnable {
    int i = 0;
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " starting");
        for (; i < 10_000; i++) {
            System.out.println(name + " i is " + i);
        }
        System.out.println(name + " finished");
    }
}

public class SimpleThreads {
    public static void main(String[] args) {
        Runnable r = new MyJob();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
//        t1.setDaemon(true);
//        t2.setDaemon(true);
        t1.start();
        t2.start();
        System.out.println(Thread.currentThread().getName() + " exiting");
    }
}
