package spring.dataaccess;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.*;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.aspectj.JtaAnnotationTransactionAspect;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Needs to add following vm arguments:
 * <p>
 * -javaagent:$HOME$/.m2/repository/org/springframework/spring-instrument/4.2.0.RELEASE/spring-instrument-4.2.0.RELEASE.jar
 * -javaagent:$HOME$/.m2/repository/org/aspectj/aspectjweaver/1.8.6/aspectjweaver-1.8.6.jar
 */
public class JtaTransactionalAspectJTest {

    @Test
    public void testSpringTransactional() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<String> messages = context.getBean("messages", List.class);

        SpringTransactionalService service = context.getBean(SpringTransactionalService.class);
        service.sendWithInTransaction("spring");

        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Transactional: send: spring"));
    }

    @Test
    public void testJtaTransactional() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<String> messages = context.getBean("messages", List.class);

        JtaTransactionalService service = context.getBean(JtaTransactionalService.class);
        service.sendWithInTransaction("jta");

        assertThat(messages.size(), is(1));
        assertThat(messages.get(0), is("Transactional: send: jta"));
    }

    @Configuration
    @Import({
            SpringTransactionalService.class
            , JtaTransactionalService.class
            , TransactionalEventListeners.class
    })
    @EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
    @EnableLoadTimeWeaving
    public static class AppConfig implements LoadTimeWeavingConfigurer {

        @Bean
        public List<String> messages() {
            return new ArrayList<>();
        }

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setName("spring-io-platform-2-poc-test")
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }

        @Bean
        public PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }


        @Bean
        public JtaAnnotationTransactionAspect transactionAspect() {
            JtaAnnotationTransactionAspect aspect = JtaAnnotationTransactionAspect.aspectOf();
            aspect.setTransactionManager(transactionManager());
            return aspect;
        }


        @Override
        public LoadTimeWeaver getLoadTimeWeaver() {
            return new InstrumentationLoadTimeWeaver();
        }

    }

    public static class TransactionalEventListeners {
        @Resource
        List<String> messages;

        @TransactionalEventListener
        public void message(String message) {
            messages.add("Transactional: " + message);
        }

    }

    public static class SpringTransactionalService {

        @Autowired
        ApplicationEventPublisher publisher;

        @Transactional
        public void sendWithInTransaction(String message) {
            publisher.publishEvent("send: " + message);
        }

    }


    public static class JtaTransactionalService {

        @Autowired
        ApplicationEventPublisher publisher;

        @javax.transaction.Transactional
        public void sendWithInTransaction(String message) {
            publisher.publishEvent("send: " + message);
        }

    }

}
