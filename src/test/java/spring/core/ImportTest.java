package spring.core;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class ImportTest {

    @Test
    public void testImportComponent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportAppConfig.class);

        EchoService echoService = context.getBean(EchoService.class);
        echoService.echo("Import");

        verify(echoService.out, times(1)).println("Import");

    }

    @Configuration
    @Import(EchoService.class)
    public static class ImportAppConfig {
        @Bean
        public PrintStream printStream() {
            return mock(PrintStream.class);
        }
    }

}
