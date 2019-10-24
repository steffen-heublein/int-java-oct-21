package threads;

import java.util.NoSuchElementException;

public class BadQueue<E> {
    private static final int CAPACITY = 10;
    private E[] data = (E[]) (new Object[CAPACITY]);
    private int count = 0;

    public BadQueue() {
    }

    public void put(E e) throws InterruptedException {

        synchronized (this) {
            while (count == CAPACITY)
                this.wait();
            data[count++] = e;
//            this.notify();
            this.notifyAll();
        }
    }

    public E take() throws InterruptedException {
        synchronized (this) {
            while (count == 0)
                this.wait();
            E rv = data[0];
            System.arraycopy(data, 1, data, 0, --count);
//            this.notify();
            this.notifyAll();
            return rv;
        }
    }

    public static void main(String[] args) {
        BadQueue<int[]> queue = new BadQueue<>();
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

//
//    public static void main(String[] args) {
//        BadQueue<String> bqs = new BadQueue<>();
//
//        bqs.put("1");
//        bqs.put("2");
//        bqs.put("3");
//        bqs.put("4");
//        bqs.put("5");
//        bqs.put("6");
//        bqs.put("7");
//        bqs.put("8");
//        bqs.put("9");
//        bqs.put("10");
////        bqs.put("10");
//
//
//        for (int i = 1; i <= 10; i++) {
//            System.out.println("Taking " + bqs.take());
//        }
////        bqs.take();
//    }
}
