package streamlab;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MorePride {
//1) Build & print a table of word-length frequency
//2) Build & print a table of frequency of counts-of-distinct-letters in the words. I.e. the word "hello" has four distinct letters, while letters has five.
//3) Build a table of letter frequency for the book & print in descending order.
//5) Calculate the ratio of "distinct letters to actual letters" in the books words.
private static final Pattern WORD_BOUDARY = Pattern.compile("\\W+");

    public static void main(String[] args) throws Throwable {
        Files.lines(Paths.get("PrideAndPrejudice.txt"))
                .filter(s -> s.length() > 0)
                .map(String::toLowerCase)
                .flatMap(WORD_BOUDARY::splitAsStream)
                .collect(Collectors.groupingBy(w -> w.length(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .map(e -> String.format("length %5d occurs %6d times", e.getKey(), e.getValue()))
                .forEach(System.out::println);
        System.out.println("-------------------------------");
        Files.lines(Paths.get("PrideAndPrejudice.txt"))
                .map(String::toLowerCase)
                .flatMap(WORD_BOUDARY::splitAsStream)
                .filter(s -> s.length() > 0)
                .map(s -> s.chars().boxed().collect(Collectors.toSet()).size())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .map(e -> String.format("distinct count %5d occurs %6d times", e.getKey(), e.getValue()))
                .forEach(System.out::println);
        System.out.println("-------------------------------");
        Files.lines(Paths.get("PrideAndPrejudice.txt"))
                .map(String::toLowerCase)
                .flatMap(WORD_BOUDARY::splitAsStream)
                .filter(s -> s.length() > 0)
                .flatMap(s -> s.chars().boxed())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .map(e -> String.format("letter %c occurs %6d times",
                        (char)(e.getKey().intValue()), e.getValue()))
                .forEach(System.out::println);
        System.out.println("-------------------------------");

        //
        Files.lines(Paths.get("PrideAndPrejudice.txt"))
                .map(String::toLowerCase)
                .flatMap(WORD_BOUDARY::splitAsStream)
                .filter(s -> s.length() > 0)
                .map(s -> new long[] {
                            s.length(),
                            s.chars().boxed().collect(Collectors.toSet()).size()
                    }
                )
                .mapToDouble(p -> (double)p[1]/p[0])
                .average()
                .ifPresent(System.out::println);
        System.out.println("-------------------------------");
    }

}
