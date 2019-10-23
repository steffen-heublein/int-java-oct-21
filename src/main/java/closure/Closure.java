package closure;

import java.util.function.Consumer;

public class Closure {
//    static int x;
    public static void main(String[] args) {
        /*final */int x = 0;
        Consumer<String> cs = s -> {
            System.out.println(s);
//            x++;
        };
//        return cs;

    }
}
