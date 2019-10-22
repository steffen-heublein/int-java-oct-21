package exceptinstream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
interface ExFunction<A, B> {
    B apply(A a) throws Throwable;
}
public class HandleFiles2 {

    public static <A, B> Function<A, Optional<B>> wrap(ExFunction<A, B> op) {
        return a -> {
            try {
                return Optional.of(op.apply(a));
            } catch (Throwable throwable) {
                return Optional.empty();
            }
        };
    }
    public static void main(String[] args) {
        Stream.of("a.txt", "b.txt", "c.txt")
                .map(wrap(name -> Files.lines(Paths.get(name))))
                .peek(opt -> {
                    if (!opt.isPresent()) {
                        System.out.println("*** Error with file ");
                    }
                })
                .filter(Optional::isPresent)
                .flatMap(Optional::get)
                .forEach(System.out::println);

    }
}
