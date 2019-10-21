package superiterable;

import students.Student;

import java.util.Arrays;
import java.util.List;

public class UseSuperIterable {
    public static void main(String[] args) {
        List<Student> schoolList = Arrays.asList(
                Student.of("Fred", 75, "Math", "Physics"),
                Student.of("Jim", 65, "Art History"),
                Student.of("Sheila", 95, "Math", "Physics", "Optics", "Quantum Mechanics")
        );
        SuperIterable<Student> school = new SuperIterable<>(schoolList);

//        for (Student s : school) {
//            System.out.println(s);
//        }

        school.forEach(s -> System.out.println(s));
        System.out.println("---------------------");
        school
                .filter(s -> s.getCourses().size() > 1)
                .map(s -> s.getName())
                .forEach(s -> System.out.println(s));
        System.out.println("---------------------");
        school
                .filter(s -> s.getCourses().size() > 1)
                .flatMap(s -> new SuperIterable<>(s.getCourses()))
                .forEach(s -> System.out.println(s));
        System.out.println("---------------------");
        schoolList.stream()
                .filter(s -> s.getCourses().size() > 1)
                .map(s -> s.getName())
                .forEach(s -> System.out.println(s));
        System.out.println("---------------------");
        schoolList.stream()
                .filter(s -> s.getCourses().size() > 1)
                .flatMap(s -> s.getCourses().stream())
                .forEach(s -> System.out.println(s));
        System.out.println("---------------------");
    }
}
