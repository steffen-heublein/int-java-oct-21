package streamlab;


import students.Student;

import java.util.Arrays;
import java.util.List;

public class Students {
    public static void main(String[] args) {
        List<Student> ls = Arrays.asList(
                Student.of("Fred", 75, "Math", "Physics"),
                Student.of("Alice", 77, "Math", "Physics", "Quantum Mechanics"),
                Student.of("Freda", 60, "Math", "Statistics"),
                Student.of("Susan", 80, "Geography", "Political Science", "History"),
                Student.of("William", 58, "Political Science"),
                Student.of("Jim", 52, "Art History"),
                Student.of("Sheila", 95, "Math", "Physics", "Quantum Mechanics", "Astrophysics")
        );
        System.out.println("1) All student names");
        ls.stream()
                .map(s -> s.getName()) // Student::getName
                .forEach(System.out::println); // s -> System.out.println(s)
        System.out.println("2) All students shown with grade");
        ls.stream()
                .map(s -> s.getName() + " has grade " + s.getGrade())
                .forEach(System.out::println);
        System.out.println("3) All \"smart\" students with grade and course count");
        ls.stream()
                .filter(s -> s.getGrade() > 3)
                .map(s -> s.getName() + " has grade " + s.getGrade() + " and takes "
                        + s.getCourses().size() + " courses")
                .forEach(System.out::println);
        System.out.println("4) All the courses taken by all students (include duplicates)");
        ls.stream()
                .flatMap(s -> s.getCourses().stream())
                .forEach(System.out::println);
        System.out.println("5) All courses, exclude duplicates");
        ls.stream()
                .flatMap(s -> s.getCourses().stream())
                .distinct()
                .forEach(System.out::println);
        System.out.println("6) All courses, in alphabetical order");
        ls.stream()
                .flatMap(s -> s.getCourses().stream())
                .distinct()
                .sorted()
                .forEach(System.out::println);
        System.out.println("7) Show all student/course combinations");
        ls.stream()
                .flatMap(s -> s.getCourses().stream().map(c -> s.getName() + " takes " + c))
                .forEach(System.out::println);
    }
}
