package threads;

class CounterJob implements Runnable {
    public int counter = 0;
    @Override
    public void run() {
        for (int i = 0; i < 10_000; i++) {
            counter++;
        }
    }
}
public class BadCounter {
    public static void main(String[] args) throws InterruptedException {
        CounterJob cj = new CounterJob();
        new Thread(cj).start();
        new Thread(cj).start();
        Thread.sleep(2000);
        System.out.println("counter is " + cj.counter);
    }
}
