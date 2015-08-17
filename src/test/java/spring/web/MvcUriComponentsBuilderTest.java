package spring.web;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Ignore
public class MvcUriComponentsBuilderTest {

    @Test
    public void test1() {


        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> requestEntity =
                RequestEntity
                        .post(URI.create("http://localhost:8080/uri/messages"))
                        .build();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);

        assertThat(responseEntity.getHeaders().getLocation(), is(URI.create("http://localhost:8080/uri/messages/msg999")));

    }

    @Test
    public void test2() {


        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> requestEntity =
                RequestEntity
                        .post(URI.create("http://127.0.0.1:8080/uri/messages"))
                        .build();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);

        assertThat(responseEntity.getHeaders().getLocation(), is(URI.create("http://127.0.0.1:8080/uri/messages/msg999")));

    }

    @Test
    public void test3() {
        UriComponentsBuilder base = UriComponentsBuilder.fromHttpUrl("http://example.com");
        URI uri = MvcUriComponentsBuilder.relativeTo(base).withMethodCall(on(TestController.class).getEntity("1")).build().toUri();
        assertThat(uri, is(URI.create("http://example.com/test/1")));
    }

    @Ignore("https://jira.spring.io/browse/SPR-13359")
    @Test
    public void test4() {
        UriComponentsBuilder base = UriComponentsBuilder.fromHttpUrl("http://example.com");
        URI uri = MvcUriComponentsBuilder.relativeTo(base).withMethodCall(on(TestController.class).getString("2")).build().toUri();
        assertThat(uri, is(URI.create("http://example.com/test/2")));
    }

    @Test
    public void test5() {
        UriComponentsBuilder base = UriComponentsBuilder.fromHttpUrl("http://example.com");

        TestController controller = on(TestController.class);
        controller.delete("3");
        URI uri = MvcUriComponentsBuilder.relativeTo(base).withMethodCall(controller).build().toUri();
        assertThat(uri, is(URI.create("http://example.com/test/3")));
    }

    @RequestMapping("test")
    public static class TestController {
        @RequestMapping("{id}")
        public String getString(@PathVariable("id") String id) {
            return "test";
        }

        @RequestMapping("{id}")
        public ResponseEntity<String> getEntity(@PathVariable("id") String id) {
            return ResponseEntity.ok("test");
        }

        @RequestMapping("{id}")
        public void delete(@PathVariable("id") String id) {
        }

    }

}
