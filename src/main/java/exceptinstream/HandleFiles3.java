package exceptinstream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

class Either<L, R> {
    private L left;
    private R right;
    private Either() {}
    public static <L, R> Either<L, R> success(R r) {
        Either<L, R> self = new Either();
        self.right = r;
        return self;
    }
    public static <L, R> Either<L, R> failure(L l) {
        Either<L, R> self = new Either();
        self.left = l;
        return self;
    }

    public boolean isSuccess() {
        return left == null;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public L getLeft() {
        if (isSuccess()) throw new NoSuchElementException("Get left from a success!");
        return left;
    }

    public R getRight() {
        if (isFailure()) throw new NoSuchElementException("Get right from a failure!");
        return right;
    }

    public <R1> Either<L, R1> mapSuccess(Function<R, R1> op) {
        if (isSuccess()) {
            return success(op.apply(right));
        } else return failure(left);
    }

    public Either<L, R> flatMapFailure(Function<L, Either<L, R>> recoverOp) {
        if (isFailure()) return recoverOp.apply(left);
        else return this;
    }
}

public class HandleFiles3 {

    public static <A, B> Function<A, Either<Throwable, B>> wrap(ExFunction<A, B> op) {
        return a -> {
            try {
                return Either.success(op.apply(a));
            } catch (Throwable throwable) {
                return Either.failure(throwable);
            }
        };
    }
    public static void main(String[] args) {
        Stream.of("a.txt", "b.txt", "c.txt")
                .map(wrap(name -> Files.lines(Paths.get(name))))
                .map(e -> e.flatMapFailure(wrap(f -> Files.lines(Paths.get("d.txt")))))
                .peek(either -> {
                    if (either.isFailure()) {
                        System.out.println("*** Error with file " + either.getLeft());
                    }
                })
                .filter(Either::isSuccess)
                .flatMap(Either::getRight)
                .forEach(System.out::println);

    }
}
