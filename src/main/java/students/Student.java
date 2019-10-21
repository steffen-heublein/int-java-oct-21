package students;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Student {
    private final String name;
    private final int grade;
    private final List<String> courses;

    public static Predicate<Student> getSmartPredicate(/*final */int threshold) {
//        threshold ++;
        return s -> s.grade > threshold;
    }

    public static Predicate<Student> getEnthousiasticPredicate(int threshold) {
        return s -> s.getCourses().size() > threshold;
    }

    public String getName() {
        return name;
    }

    private Student(String name, int grade, List<String> courses) {
        this.name = name;
        this.grade = grade;
        this.courses = courses;
    }

    public static Student of(String name, int grade, String ... courses) {
        return new Student(name, grade, Arrays.asList(courses)); // potentially mutable courses!
    }

    public int getGrade() {
        return grade;
    }

    public List<String> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                ", courses=" + courses +
                '}';
    }
}
