package spring.dataaccess;


import com.example.domain.model.Todo;
import com.example.domain.repository.TodoRepository;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;
public class Hibernate5Test {

    @Test
    public void testInsert() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

        Todo newTodo = new Todo();
        newTodo.setTodoId(UUID.randomUUID().toString());
        newTodo.setTodoTitle("title");
        newTodo.setFinished(false);
        newTodo.setCreatedAt(new Date());

        Todo savedTodo = todoRepository.save(newTodo);


        Todo todo = jdbcTemplate.queryForObject("select * from todo where todo_id = ?", new BeanPropertyRowMapper<>(Todo.class), savedTodo.getTodoId());

        assertThat(todo.getTodoId(),is(savedTodo.getTodoId()));
        assertThat(todo.getTodoTitle(),is("title"));
        assertThat(todo.isFinished(),is(false));
        assertThat(todo.getCreatedAt().getTime(),is(savedTodo.getCreatedAt().getTime()));


    }

    @Test
    public void testUpdate() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

        Todo newTodo = new Todo();
        newTodo.setTodoId(UUID.randomUUID().toString());
        newTodo.setTodoTitle("title");
        newTodo.setFinished(false);
        newTodo.setCreatedAt(new Date());

        Todo savedTodo = todoRepository.save(newTodo);

        savedTodo.setTodoTitle("modified title");
        savedTodo.setFinished(true);

        Todo updatedTodo = todoRepository.save(savedTodo);

        Todo todo = jdbcTemplate.queryForObject("select * from todo where todo_id = ?", new BeanPropertyRowMapper<>(Todo.class), savedTodo.getTodoId());

        assertThat(todo.getTodoId(),is(savedTodo.getTodoId()));
        assertThat(todo.getTodoTitle(),is("modified title"));
        assertThat(todo.isFinished(),is(true));
        assertThat(todo.getCreatedAt().getTime(),is(savedTodo.getCreatedAt().getTime()));


    }

    @Test(expected = EmptyResultDataAccessException.class)
         public void testDelete() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

        Todo newTodo = new Todo();
        newTodo.setTodoId(UUID.randomUUID().toString());
        newTodo.setTodoTitle("title");
        newTodo.setFinished(false);
        newTodo.setCreatedAt(new Date());

        Todo savedTodo = todoRepository.save(newTodo);

        savedTodo.setTodoTitle("modified title");
        savedTodo.setFinished(true);

        todoRepository.delete(savedTodo.getTodoId());

        jdbcTemplate.queryForObject("select * from todo where todo_id = ?", new BeanPropertyRowMapper<>(Todo.class), savedTodo.getTodoId());

    }


    @Test
    public void testSelect() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

        Todo newTodo = new Todo();
        newTodo.setTodoId(UUID.randomUUID().toString());
        newTodo.setTodoTitle("title");
        newTodo.setFinished(false);
        newTodo.setCreatedAt(new Date());

        Todo savedTodo = todoRepository.save(newTodo);

        Todo todo = todoRepository.findOne(savedTodo.getTodoId());

        assertThat(todo.getTodoId(),is(savedTodo.getTodoId()));
        assertThat(todo.getTodoTitle(),is("title"));
        assertThat(todo.isFinished(),is(false));
        assertThat(todo.getCreatedAt().getTime(),is(savedTodo.getCreatedAt().getTime()));

    }

    @Configuration
    @ImportResource( "classpath:META-INF/spring/applicationContext.xml")
    public static class AppConfig {
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

}
