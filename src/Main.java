package tubes;

import static tubes.Console.*;

public class Main {
    public static void main(String[] args) {
        ConsoleApp.start(args[0].equalsIgnoreCase("testMode"));
    }
}
