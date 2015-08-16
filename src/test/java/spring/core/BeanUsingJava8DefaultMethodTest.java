package spring.core;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class BeanUsingJava8DefaultMethodTest {

    @Test
    public void testDefaultAppConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DefaultAppConfig.class);

        PrintService printService = context.getBean(PrintService.class);
        printService.println();

        verify(printService.out, times(1)).println("Default");
    }

    @Test
    public void testOverrideAppConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OverrideAppConfig.class);

        PrintService printService = context.getBean(PrintService.class);
        printService.println();

        verify(printService.out, times(1)).println("Override");

    }

    @Configuration
    public static class DefaultAppConfig implements DefaultConfig {

    }

    @Configuration
    public static class OverrideAppConfig implements DefaultConfig {
        @Override
        public PrintService printService() {
            return new PrintService("Override");
        }
    }

    public interface DefaultConfig {
        @Bean
        default PrintService printService() {
            return new PrintService("Default");
        }

        @Bean
        default PrintStream printStream() {
            return mock(PrintStream.class);
        }

    }

}
