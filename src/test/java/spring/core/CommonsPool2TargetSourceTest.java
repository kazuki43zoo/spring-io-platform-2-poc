package spring.core;


import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.aop.target.PoolingConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CommonsPool2TargetSourceTest {


    @Test
    public void testGetTasks() throws InterruptedException, NoSuchMethodException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(3);

        List<NoSuchElementException> noSuchElementExceptions = new ArrayList<>();
        Thread t1 = new Thread(() -> {
            try {
                TestService testService = context.getBean("testService", TestService.class);
                latch.countDown();
                testService.echo("message1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishLatch.countDown();
        });

        Thread t2 = new Thread(() -> {
            try {
                latch.await();
                TestService testService = context.getBean("testService", TestService.class);
                testService.echo("message2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finishLatch.countDown();
        });

        Thread t3 = new Thread(() -> {
            try {
                latch.await();
                Thread.sleep(100);
                TestService testService = context.getBean("testService", TestService.class);
                testService.echo("message3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                noSuchElementExceptions.add(e);
            }
            finishLatch.countDown();
        });
        t1.start();
        t2.start();
        t3.start();

        finishLatch.await();

        PrintStream out = context.getBean(PrintStream.class);

        verify(out, times(1)).println("message1");
        verify(out, times(1)).println("message2");
        verify(out, never()).println("message3");

        assertThat(noSuchElementExceptions.size(), is(1));

        PoolingConfig poolingConfig = context.getBean(PoolingConfig.class);

        assertThat(poolingConfig.getMaxSize(),is(1));
        assertThat(poolingConfig.getActiveCount(),is(0));
        assertThat(poolingConfig.getIdleCount(),is(1));


    }

    @Configuration
    public static class AppConfig {
        @Bean
        public PrintStream printStream() {
            return mock(PrintStream.class);
        }

        @Bean
        @Scope("prototype")
        public TestService testServiceTarget() {
            return new TestService();
        }

        @Bean
        public CommonsPool2TargetSource commonsPool2TargetSource() {
            CommonsPool2TargetSource targetSource = new CommonsPool2TargetSource();
            targetSource.setTargetBeanName("testServiceTarget");
            targetSource.setMaxSize(1);
            targetSource.setMaxIdle(1);
            targetSource.setMaxWait(600);
            return targetSource;
        }

        @Bean
        public ProxyFactoryBean testService() {
            ProxyFactoryBean factoryBean = new ProxyFactoryBean();
            factoryBean.setTargetSource(commonsPool2TargetSource());
            return factoryBean;
        }


    }

    public static class TestService {

        @Inject
        PrintStream out;

        public void echo(String message) throws InterruptedException {
            Thread.sleep(500);
            out.println(message);
        }
    }


}
