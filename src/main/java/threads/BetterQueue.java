package threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BetterQueue<E> {
    private static final int CAPACITY = 10;
    private E[] data = (E[]) (new Object[CAPACITY]);
    private int count = 0;

    ReentrantLock lock = new ReentrantLock();
    Condition NOT_FULL = lock.newCondition();
    Condition NOT_EMPTY = lock.newCondition();

    public BetterQueue() {
    }

    public void put(E e) throws InterruptedException {

        lock.lock();
        try {
            while (count == CAPACITY)
                NOT_FULL.await();
            data[count++] = e;
            NOT_EMPTY.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                NOT_EMPTY.await();
            E rv = data[0];
            System.arraycopy(data, 1, data, 0, --count);
            NOT_FULL.signal();
            return rv;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BetterQueue<int[]> queue = new BetterQueue<>();
        new Thread(()->{
            System.out.println("Producer starting");
            for (int i = 0; i < 1_000; i++) {
                try {
                    int[] data = {i, 0};
                    if (i < 100) Thread.sleep(1);
                    data[1] = i;
                    if (i == 500) data[0] = -1;
//                    System.out.println("putting " + i + " [0] = " + data[0] + " [1] = " + data[1]);
                    queue.put(data); data = null;
                } catch (InterruptedException ie) {
                    System.out.println("Interrupted???");
                }
            }
            System.out.println("Producer finished");
        }).start();
        new Thread(()->{
            System.out.println("Consumer starting");
            for (int i = 0; i < 1_000; i++) {
                try {
                    int[] data = queue.take();
//                    System.out.println("i " + i + " [0] = " + data[0] + " [1] = " + data[1]);
                    if (i > 900) Thread.sleep(1);
                    if (data[0] != i || data[1] != i) {
                        System.out.println("**** ERROR " + i + " [0] = " + data[0] + " [1] = " + data[1]);
                    }
                } catch (InterruptedException ie) {
                    System.out.println("Interrupted???");
                }
            }
            System.out.println("Consumer finished");
        }).start();
        System.out.println("Producer and consumer started");
    }
}
