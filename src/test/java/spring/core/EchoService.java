package spring.core;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintStream;

public class EchoService {
    @Autowired(required = false)
    PrintStream out = System.out;

    public void echo(String message) {
        out.println(message);
    }
}
