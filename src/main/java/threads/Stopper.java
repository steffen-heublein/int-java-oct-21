package threads;

public class Stopper implements Runnable {
    volatile boolean stop = false;

    @Override
    public void run() {
        while (!stop) {
        }
        System.out.println("Stopping!");
    }

    public static void main(String[] args) throws Throwable {
        Stopper stopper = new Stopper();
        new Thread(stopper).start();
        Thread.sleep(1000);
        stopper.stop = true;
        System.out.println("Main exiting");
    }
}
