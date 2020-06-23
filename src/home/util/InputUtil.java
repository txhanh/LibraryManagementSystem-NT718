package home.util;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputUtil {

    public InputUtil() throws IOException {
        String line = waitTillUserHitsEnter();
        System.out.println(line);
    }

    public static String waitTillUserHitsEnter(String message)
            throws IOException {
        System.out.println(message);
        return waitTillUserHitsEnter();
    }

    public static String waitTillUserHitsEnter()
            throws IOException {
        System.out.println("Nhấn nút Enter để tiếp tục...");
        BufferedReader standardInput = new BufferedReader(
                new InputStreamReader(System.in));
        String line = null;
        line = standardInput.readLine();
        return line;
    }
}