package streamlab;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonteCarlo {
    public static void main(String[] args) {
        final int SAMPLE_SIZE = 100_000;
        Map<Integer, Long> table = Stream.generate(() ->
                ThreadLocalRandom.current().ints(1, 7).limit(10).sum())
                .limit(SAMPLE_SIZE)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        long max_count = table.entrySet().stream().mapToLong(Map.Entry::getValue).max().getAsLong();
        long ratio = max_count / 100;
        table.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> String.format("%2d: %s", e.getKey(),
                        Stream.generate(() -> "*").limit(e.getValue() / ratio)
                                .collect(Collectors.joining())))
                .forEach(System.out::println);
    }
}
