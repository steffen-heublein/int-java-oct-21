package hello;

import java.util.function.Consumer;

public class Hello {
    public static void main(String[] args) {
//        ((Consumer<String>)(var s) -> System.out.println(s)).accept(
//                switch(args.length) {
//                    case 0, 1 -> "Hello World";
//                    default -> { yield "Unknown"; }
//                }
//        );
        ((Runnable)()-> System.out.println("Hello World")).run();
    }
}
