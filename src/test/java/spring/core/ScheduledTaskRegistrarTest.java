package spring.core;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class ScheduledTaskRegistrarTest {

    @Test
    public void testGetTasks() throws InterruptedException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

        Thread.sleep(500);

        verify(out, times(1)).println(ScheduledService.class.getMethod("echo"));

    }

    @Configuration
    @Import(ScheduledService.class)
    @EnableScheduling
    public static class AppConfig {
        @Bean
        public PrintStream printStream() {
            return mock(PrintStream.class);
        }

    }

    public static class ScheduledService implements SchedulingConfigurer {

        ScheduledTaskRegistrar registrar;

        @Autowired
        PrintStream out;

        @Scheduled(fixedRate = 1000)
        public void echo() {
            out.println(((ScheduledMethodRunnable) registrar.getFixedRateTaskList().get(0).getRunnable()).getMethod());
        }

        @Override
        public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
            registrar = taskRegistrar;
        }

    }


}
