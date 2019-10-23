package concurrent;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

public class ParStream {
    public static void main(String[] args) {
        Object sync = new Object();
        LongAdder count = new LongAdder();
//        AtomicLong count = new AtomicLong();
//        long [] count = {0};
        long start = System.nanoTime();
        long streamCount =
                Stream.generate(() -> ThreadLocalRandom.current().nextInt())
                .limit(3_000_000_000L)
//                .parallel()
//                .peek(x -> System.out.println("processing " + x))
//                .peek(x -> {synchronized (sync){count[0]++;}})
//                .peek(x -> count.incrementAndGet())
                .peek(x -> count.increment())
                .count()
                ;
        double time = (System.nanoTime() - start) / 1_000_000_000.0;
//        System.out.println("Stream count " + streamCount + " and count " + count[0]
//                + " time " + time + " rate " + (count[0] / time));
//        System.out.println("Stream count " + streamCount + " and count " + count.get()
//                + " time " + time + " rate " + (count.get() / time));
        System.out.println("Stream count " + streamCount + " and count " + count.sum()
                + " time " + time + " rate " + (count.sum() / time));
    }
}
