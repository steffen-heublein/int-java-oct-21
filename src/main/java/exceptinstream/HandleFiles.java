package exceptinstream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class HandleFiles {
    public static Optional<Stream<String>> getLines(String name) {
        try {
            return Optional.of(Files.lines(Paths.get(name)));
        } catch (Throwable t) {
            return Optional.empty();
        }
    }
    public static void main(String[] args) {
        Stream.of("a.txt", "b.txt", "c.txt")
                .map(HandleFiles::getLines)
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
