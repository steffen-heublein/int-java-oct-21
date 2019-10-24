package reductions;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class MutableAverage {
    private double sum = 0;
    private long count = 0;

    public MutableAverage() {}

    public void include(double d) {
        sum += d;
        count ++;
    }

    public void merge(MutableAverage other) {
        sum += other.sum;
        count += other.count;
    }

    public Optional<Double> get() {
        if (count > 0) {
            return Optional.of(sum / count);
        } else {
            return Optional.empty();
        }
    }
}

public class CollectingAverager {
    public static void main(String[] args) {
        long start = System.nanoTime();
        ThreadLocalRandom.current().doubles(1_200_000_000L, -Math.PI, +Math.PI)
//        Stream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
//        Stream.iterate(0.0, x -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
//                .limit(1_200_000_000)
//                .unordered()
                .parallel()
//                .boxed()
                .map(Math::sin)
                .collect(MutableAverage::new, MutableAverage::include, MutableAverage::merge)
        .get()
        .ifPresent(m -> System.out.println("mean " + m));
        long time = System.nanoTime() - start;
        System.out.println("Time = " + (time / 1_000_000_000.0));
    }
}
