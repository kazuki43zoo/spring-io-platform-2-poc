package spring.core;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class StandardScriptFactoryTest {

    @Test
    public void testJavaScript() throws InterruptedException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

       MessageProvider messageProvider = context.getBean("javaScriptMessageProvider", MessageProvider.class);

        String message = messageProvider.getMessage();

        assertThat(message, is("Hello world!! provided by JavaScript"));

    }

    @Test
    public void testGroovy() throws InterruptedException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

        MessageProvider messageProvider = context.getBean("groovyMessageProvider", MessageProvider.class);

        String message = messageProvider.getMessage();

        assertThat(message, is("Hello world!! provided by Groovy"));

    }

    @Test
    public void testJRuby() throws InterruptedException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

        MessageProvider messageProvider = context.getBean("jrubyMessageProvider", MessageProvider.class);

        String message = messageProvider.getMessage();

        assertThat(message, is("Hello world!! provided by JRuby"));

    }

    @Configuration
    @ImportResource(locations = "classpath:spring/core/StandardScriptFactoryTest-context.xml")
    @EnableScheduling
    public static class AppConfig {
        @Bean
        public PrintStream printStream() {
            return mock(PrintStream.class);
        }

    }


}
