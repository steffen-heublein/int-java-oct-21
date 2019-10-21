package superiterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable<E> implements Iterable<E> {
    private Iterable<E> self;

    public SuperIterable(Iterable<E> self) {
        this.self = self;
    }

//    public void doToAll(Consumer<E> op) {
//        for (E s : self) {
//            op.accept(s);
//        }
//    }

//    public <F> SuperIterable<F> map(Function<E, F> op) {
//        List<F> out = new ArrayList<>();
//        for (E e : self) {
//            out.add(op.apply(e));
//        }
//        return new SuperIterable<>(out);
//    }

    public <F> SuperIterable<F> map(Function<E, F> op) {
        List<F> out = new ArrayList<>();
        self.forEach(e -> out.add(op.apply(e)));
        return new SuperIterable<>(out);
    }

    public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> op) {
        List<F> out = new ArrayList<>();
        self.forEach(e -> op.apply(e).forEach(f -> out.add(f)));
        return new SuperIterable<>(out);
    }

    public SuperIterable<E> filter(Predicate<E> crit) {
        List<E> rv = new ArrayList<>();
        for (E s : self) {
            if (crit.test(s)) {
                rv.add(s);
            }
        }
        return new SuperIterable<>(rv);
    }



    @Override
    public Iterator<E> iterator() {
        return self.iterator();
    }
}
