package spring.core;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class TransactionalEventListenerTest {

    @Test
    public void testHandleWithinSpringTransaction() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<String> messages = context.getBean("messages", List.class);

        SpringTransactionalService service = context.getBean(SpringTransactionalService.class);
        service.sendWithInTransaction("hello");

        assertThat(messages.size(), is(4));
        assertThat(messages.get(0), is("send: start"));
        assertThat(messages.get(1), is("send: end"));
        assertThat(messages.get(2), is("TransactionalEventListener(after commit): send: hello"));
        assertThat(messages.get(3), is("TransactionalEventListener(fallbackExecution=true): send: hello"));
    }

    @Test
    public void testHandleWithinJtaTransaction() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<String> messages = context.getBean("messages", List.class);

        JtaTransactionalService service = context.getBean(JtaTransactionalService.class);
        service.sendWithInTransaction("hello");

        assertThat(messages.size(), is(4));
        assertThat(messages.get(0), is("send: start"));
        assertThat(messages.get(1), is("send: end"));
        assertThat(messages.get(2), is("TransactionalEventListener(after commit): send: hello"));
        assertThat(messages.get(3), is("TransactionalEventListener(fallbackExecution=true): send: hello"));
    }

    @Test
    public void testHandleWithoutTransaction() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<String> messages = context.getBean("messages", List.class);

        SpringTransactionalService service = context.getBean(SpringTransactionalService.class);
        service.sendWithoutTransaction("hello");

        assertThat(messages.size(), is(3));
        assertThat(messages.get(0), is("send: start"));
        assertThat(messages.get(1), is("TransactionalEventListener(fallbackExecution=true): send: hello"));
        assertThat(messages.get(2), is("send: end"));
    }


    @Configuration
    @Import({
            TransactionalEventListeners.class
            , SpringTransactionalService.class
            , JtaTransactionalService.class
    })
    @EnableTransactionManagement
    public static class AppConfig {
        @Bean
        public PrintStream printStream() {
            return mock(PrintStream.class);
        }

        @Bean
        public List<String> messages() {
            return new ArrayList<>();
        }
        @Bean
        public DataSource dataSource(){
            return new EmbeddedDatabaseBuilder()
                    .setName("spring-io-platform-2-poc-test")
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }
        @Bean
        public PlatformTransactionManager transactionManager(){
            return new DataSourceTransactionManager(dataSource());
        }
    }


    public static class TransactionalEventListeners {
        @Resource
        List<String> messages;

        @TransactionalEventListener
        @Order(0)
        public void message(String message) {
            messages.add("TransactionalEventListener(after commit): " + message);
        }

        @TransactionalEventListener(fallbackExecution = true)
        @Order(1)
        public void messageOnNoTran(String message) {
            messages.add("TransactionalEventListener(fallbackExecution=true): " + message);
        }

    }


    public static class SpringTransactionalService {

        @Resource
        List<String> messages;

        @Autowired
        ApplicationEventPublisher publisher;

        @Transactional
        public void sendWithInTransaction(String message) {
            messages.add("send: start");
            publisher.publishEvent("send: " + message);
            messages.add("send: end");
        }

        public void sendWithoutTransaction(String message) {
            messages.add("send: start");
            publisher.publishEvent("send: " + message);
            messages.add("send: end");
        }

    }


    public static class JtaTransactionalService {

        @Resource
        List<String> messages;

        @Autowired
        ApplicationEventPublisher publisher;

        @javax.transaction.Transactional
        public void sendWithInTransaction(String message) {
            messages.add("send: start");
            publisher.publishEvent("send: " + message);
            messages.add("send: end");
        }

    }

}
