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
        }
    }

    public E take() throws InterruptedException {
        synchronized (this) {
            while (count == 0)
                this.wait();
            E rv = data[0];
            System.arraycopy(data, 1, data, 0, --count);
            this.notify();
            return rv;
        }
    }

    public static void main(String[] args) {
        BadQueue<String> bqs = new BadQueue<>();

        bqs.put("1");
        bqs.put("2");
        bqs.put("3");
        bqs.put("4");
        bqs.put("5");
        bqs.put("6");
        bqs.put("7");
        bqs.put("8");
        bqs.put("9");
        bqs.put("10");
//        bqs.put("10");


        for (int i = 1; i <= 10; i++) {
            System.out.println("Taking " + bqs.take());
        }
//        bqs.take();
    }
}
