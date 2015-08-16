package spring.web;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

}
