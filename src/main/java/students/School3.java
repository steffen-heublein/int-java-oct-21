package students;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

interface Criterion<E> {
    boolean test(E e);
    default Criterion<E> negate() {
        return e -> !this.test(e);
    }
    static <E> Criterion<E> and(Criterion<E> first, Criterion<E> second) {
        return e -> first.test(e) && second.test(e);
    }
}

public class School3 {
    public static <E> Predicate<E> not(Predicate<E> ps) {
        return s -> !ps.test(s);
    }

    public static <E> Predicate<E> and(Predicate<E> first, Predicate<E> second) {
        return e -> first.test(e) && second.test(e);
    }

    public static <E> Predicate<E> or(Predicate<E> first, Predicate<E> ... rest) {
        return e -> {
            boolean result = first.test(e);
            Iterator<Predicate<E>> ipe = Arrays.asList(rest).iterator();
            while (!result && ipe.hasNext()) {
                result = ipe.next().test(e);
            }
            return result;
        };
    }

    public static <E> void doToAll(Iterable<E> ls, Consumer<E> op) {
        for (E s : ls) {
            op.accept(s);
        }
    }

    public static <E> List<E> select(Iterable<E> ls, Predicate<E> crit) {
        List<E> rv = new ArrayList<>();
        for (E s : ls) {
            if (crit.test(s)) {
                rv.add(s);
            }
        }
        return rv;
    }

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                Student.of("Fred", 75, "Math", "Physics"),
                Student.of("Jim", 65, "Art History"),
                Student.of("Sheila", 95, "Math", "Physics", "Optics", "Quantum Mechanics")
        );
        doToAll(
                select(students, Student.getSmartPredicate(70)),
                s-> System.out.println("> " + s));
        System.out.println("Enthusiastic --------------------");
        Predicate<Student> enthusiastic = s -> s.getCourses().size() > 2;
        doToAll(select(students, enthusiastic),
                s -> System.out.println(">> " + s));

        System.out.println("Not Enthusiastic --------------------");
//        doToAll(select(students, not(enthusiastic)),
        doToAll(select(students, enthusiastic.negate()),
                s -> System.out.println(">> " + s));

        System.out.println("--------------------");
        doToAll(
                select(students, s -> s.getCourses().size() < 3 && s.getGrade() > 70),
                s -> System.out.println("Smart but not very enthusiastic: " + s)
        );

        System.out.println("--------------------");
        doToAll(Arrays.asList("Fred", "Jim", "Sheila"), s -> System.out.println(s));
    }
}
