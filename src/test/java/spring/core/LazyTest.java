package spring.core;

import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class LazyTest {

    @Test
    public void testResource() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Service service = context.getBean(Service.class);

        try {
            service.println();
            fail();
        } catch (NoSuchBeanDefinitionException e) {
        }

    }

    @Configuration
    public static class AppConfig {
        @Bean
        public DataSource dataSource() {
            return mock(DataSource.class);
        }

        @Bean
        public Service service() {
            return new Service();
        }
    }

    public static class Service {

        @Resource(name = "jdbc/dataSource")
        @Lazy
        DataSource dataSource;

        public void println() {
            try {
                dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
