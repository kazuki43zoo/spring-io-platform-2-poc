package spring.core;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class AnnotationBasedEventListenerTest {

    @Test
    public void testHandleBuiltInEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);
        verify(out, times(1)).println("ContextRefreshedEvent");
        verify(out, times(1)).println("ContextRefreshedEvent by no argument listener method");
    }

    @Test
    public void testHandleCustomEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

        context.publishEvent(new CustomEvent(this, "test1"));

        verify(out, times(1)).println("CustomEvent:test1");
        verify(out, times(1)).println("CustomEvent by no argument listener method");
    }

    @Test
    public void testHandlePayloadEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

        context.publishEvent("test2");

        verify(out, times(1)).println("Payload:test2");
        verify(out, times(1)).println("PayloadEvent:test2");
        verify(out, times(1)).println("PayloadEvent by no argument listener method");
    }

    @Test
    public void testHandleEventUsingFiltering() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

        context.publishEvent("match");
        context.publishEvent("no match");

        verify(out, times(1)).println("FilteringPayloadApplicationEvent:match");
        verify(out, times(1)).println("FilteringPayloadApplicationEvent with root:match");
        verify(out, times(1)).println("FilteringPayload:match");
        verify(out, times(1)).println("FilteringPayload with root:match");
        verify(out, times(1)).println("FilteringPayload with a0:match");
        verify(out, times(1)).println("FilteringPayload with p0:match");
        verify(out, never()).println("FilteringPayloadApplicationEvent:no match");
        verify(out, never()).println("FilteringPayloadApplicationEvent with root:no match");
        verify(out, never()).println("FilteringPayload:no match");
        verify(out, never()).println("FilteringPayload with root:no match");
        verify(out, never()).println("FilteringPayload with a0:no match");
        verify(out, never()).println("FilteringPayload with p0:no match");

    }


    @Test
    public void testHandleWithOrdering() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        List<String> messages = context.getBean("messages", List.class);

        context.publishEvent("test");

        assertThat(messages.size(), is(2));
        assertThat(messages.get(0), is("order 0:test"));
        assertThat(messages.get(1), is("order 1:test"));


    }


    @Test
    public void testHandleEventUsingChain() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        PrintStream out = context.getBean(PrintStream.class);

        context.publishEvent("root");

        verify(out, times(1)).println("ChainEvent on root:root");
        verify(out, times(1)).println("ChainEvent on child:child");

    }


    @Configuration
    @Import({
            BuiltOnEventListeners.class
            , CustomEventListeners.class
            , PayloadEventListeners.class
            , FilteringEventListeners.class
            , OrderingEventListeners.class
            , ChainEventListeners.class
    })
    public static class AppConfig {
        @Bean
        public PrintStream printStream() {
            return mock(PrintStream.class);
        }

        @Bean
        public List<String> messages() {
            return new ArrayList<>();
        }

    }

    public static class BuiltOnEventListeners {
        @Autowired
        PrintStream out;

        @EventListener
        public void contextRefreshed(ContextRefreshedEvent event) {
            out.println("ContextRefreshedEvent");
        }

        @EventListener(classes = ContextRefreshedEvent.class)
        public void contextRefreshed() {
            out.println("ContextRefreshedEvent by no argument listener method");
        }
    }


    public static class CustomEventListeners {
        @Autowired
        PrintStream out;

        @EventListener
        public void customEvent(CustomEvent event) {
            out.println("CustomEvent:" + event.getMessage());
        }

        @EventListener(classes = ContextRefreshedEvent.class)
        public void customEvent() {
            out.println("CustomEvent by no argument listener method");
        }
    }

    public static class PayloadEventListeners {
        @Autowired
        PrintStream out;

        @EventListener
        public void message(String message) {
            out.println("Payload:" + message);
        }

        @EventListener
        public void message(PayloadApplicationEvent<String> event) {
            out.println("PayloadEvent:" + event.getPayload());
        }

        @EventListener(classes = String.class)
        public void message() {
            out.println("PayloadEvent by no argument listener method");
        }

    }

    public static class FilteringEventListeners {
        @Autowired
        PrintStream out;

        @EventListener(condition = "#event.payload == 'match'")
        public void messageUsingFiltering(PayloadApplicationEvent<String> event) {
            out.println("FilteringPayloadApplicationEvent:" + event.getPayload());
        }

        @EventListener(condition = "#root.event.payload == 'match'")
        public void messageUsingFilteringWithRoot(PayloadApplicationEvent<String> event) {
            out.println("FilteringPayloadApplicationEvent with root:" + event.getPayload());
        }

        @EventListener(condition = "#message == 'match'")
        public void messageUsingFiltering(String message) {
            out.println("FilteringPayload:" + message);
        }

        @EventListener(condition = "#root.args[0] == 'match'")
        public void messageUsingFilteringWithRoot(String message) {
            out.println("FilteringPayload with root:" + message);
        }

        @EventListener(condition = "#a0 == 'match'")
        public void messageUsingFilteringWithA0(String message) {
            out.println("FilteringPayload with a0:" + message);
        }

        @EventListener(condition = "#p0 == 'match'")
        public void messageUsingFilteringWithP0(String message) {
            out.println("FilteringPayload with p0:" + message);
        }

    }

    public static class OrderingEventListeners {
        @Resource
        List<String> messages;

        @EventListener
        @Order(1)
        public void message1(String message) {
            messages.add("order 1:" + message);
        }

        @EventListener
        @Order(0)
        public void message2(String message) {
            messages.add("order 0:" + message);
        }
    }

    public static class ChainEventListeners {
        @Autowired
        PrintStream out;

        @EventListener(condition = "#message == 'root'")
        public String root(String message) {
            out.println("ChainEvent on root:" + message);
            return "child";
        }

        @EventListener(condition = "#message == 'child'")
        public void child(String message) {
            out.println("ChainEvent on child:" + message);
        }
    }

    public static class CustomEvent extends ApplicationEvent {

        private final String message;

        public CustomEvent(Object source, String message) {
            super(source);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }


}
