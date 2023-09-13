package classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static void Convert(String arg) {
        List<String> command= new ArrayList<String>();
        command.add("python3");
        command.add("converter.py");
        command.add(arg);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        int exitCode;
        try {
            Process process = processBuilder.start();
            exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(exitCode);
    }
    public static void Convert() {
        Convert("");
    }



}
