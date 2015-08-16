package spring.core;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintStream;

public class PrintService {
    @Autowired(required = false)
    PrintStream out = System.out;
    private final String message;

    public PrintService(String message) {
        this.message = message;
    }

    public void println() {
        out.println(message);
    }
}
