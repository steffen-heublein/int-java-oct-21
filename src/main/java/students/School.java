package students;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface DoToStudent {
    void accept(Student s);
}

interface StudentCriterion {
    boolean test(Student s);
}

class SmartStudentCriterion implements StudentCriterion {
    @Override
    public boolean test(Student s) {
        return s.getGrade() > 70;
    }
}

class EnthusiasticStudentCriterion implements StudentCriterion {
    @Override
    public boolean test(Student s) {
        return s.getCourses().size() > 2;
    }
}

public class School {
    public static void showStudents(Iterable<Student> ls) {
        for (Student s : ls) {
            System.out.println("> " + s);
        }
    }

    public static void doToAllStudents(Iterable<Student> ls, DoToStudent op) {
        for (Student s : ls) {
            op.accept(s);
        }
    }

//    public static List<Student> selectSmartStudents(Iterable<Student> ls, int threshold) {
//        List<Student> rv = new ArrayList<>();
//        for (Student s : ls) {
//            if (s.getGrade() > threshold) {
//                rv.add(s);
//            }
//        }
//        return rv;
//    }

    public static List<Student> selectStudents(Iterable<Student> ls, StudentCriterion crit) {
        List<Student> rv = new ArrayList<>();
        for (Student s : ls) {
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
        showStudents(students);
        System.out.println("--------------------");
//        showStudents(selectSmartStudents(students, 70));
        showStudents(selectStudents(students, new SmartStudentCriterion()));
        System.out.println("--------------------");
        showStudents(selectStudents(students, new EnthusiasticStudentCriterion()));

        System.out.println("--------------------");
        showStudents(selectStudents(students, (Student s) -> {
            return s.getGrade() < 80;
        }));

        StudentCriterion o = s -> {
//            this.wait(); // no "this" created by a lambda, this context is static, so none exists.
            return s.getGrade() < 80;
        };

        System.out.println("--------------------");
        doToAllStudents(
                selectStudents(students, s -> s.getCourses().size() < 3 && s.getGrade() > 70),
                s -> System.out.println("Smart but not very enthusiastic: " + s)
        );
    }
}
