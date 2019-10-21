package students;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class School3 {
    public static <E> Predicate<E> not(Predicate<E> ps) {
        return s -> !ps.test(s);
    }

    public static <E> void doToAllStudents(Iterable<E> ls, Consumer<E> op) {
        for (E s : ls) {
            op.accept(s);
        }
    }

    public static <E> List<E> selectStudents(Iterable<E> ls, Predicate<E> crit) {
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
        doToAllStudents(
                selectStudents(students, Student.getSmartPredicate(70)),
                s-> System.out.println("> " + s));
        System.out.println("Enthusiastic --------------------");
        Predicate<Student> enthusiastic = s -> s.getCourses().size() > 2;
        doToAllStudents(selectStudents(students, enthusiastic),
                s -> System.out.println(">> " + s));

        System.out.println("Not Enthusiastic --------------------");
        doToAllStudents(selectStudents(students, not(enthusiastic)),
                s -> System.out.println(">> " + s));

        System.out.println("--------------------");
        doToAllStudents(
                selectStudents(students, s -> s.getCourses().size() < 3 && s.getGrade() > 70),
                s -> System.out.println("Smart but not very enthusiastic: " + s)
        );

        System.out.println("--------------------");
        doToAllStudents(Arrays.asList("Fred", "Jim", "Sheila"), s -> System.out.println(s));
    }
}
