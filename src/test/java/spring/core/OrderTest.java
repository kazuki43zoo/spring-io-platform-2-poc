package spring.core;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.io.PrintStream;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class OrderTest {

    @Test
    public void testOrderConfigurations() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OrderAppConfig.class, Order1Config.class, Order0Config.class);
        System.out.println(Arrays.asList(context.getBeanDefinitionNames()));
        PrintService printService = context.getBean(PrintService.class);
        printService.println();

        verify(printService.out, times(1)).println("Order1");

    }

    @Configuration
    public static class OrderAppConfig {
        @Bean
        public PrintStream printStream() {
            return mock(PrintStream.class);
        }
    }


    @Configuration
    @Order(0)
    public static class Order0Config {
        @Bean
        public PrintService printService() {
            return new PrintService("Order0");
        }
    }


    @Configuration
    @Order(1)
    public static class Order1Config {
        @Bean
        public PrintService printService() {
            return new PrintService("Order1");
        }
    }

}
