package reductions;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;

class Average {
    public final double sum;
    public final long count;

    // creates the "identity" data value for the Monoid composed of the type "Average",
    // and the operation "include"
    public Average() {
        this(0, 0);
    }

    public Average(double sum, long count) {
        this.sum = sum;
        this.count = count;
    }

    public Average include(double d) {
        return new Average(this.sum + d, this.count + 1);
    }

    public Average merge(Average other) {
        return new Average(this.sum + other.sum, this.count + other.count);
    }
}

public class ReducingAverager {
    public static void main(String[] args) {
//        BinaryOperator<Average> dummy = (a, b) -> { throw new RuntimeException("Shouldn't happen"); };
        long start = System.nanoTime();
        Average result = ThreadLocalRandom.current().doubles(3_000_000_000L, -Math.PI, +Math.PI)
                .parallel()
                .boxed()
                .reduce(new Average(), Average::include, Average::merge);
//                .reduce(new Average(), (a, d) -> a.include(d), (a, a1) -> a.merge(a1));
        long time = System.nanoTime() - start;
        System.out.println("Sum = " + result.sum + " count = " + result.count
                + " mean = " + (result.sum / result.count) +
                " time = " + (time / 1_000_000_000.0));
    }
}
