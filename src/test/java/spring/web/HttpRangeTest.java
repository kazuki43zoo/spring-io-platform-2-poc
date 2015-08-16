package spring.web;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Ignore
public class HttpRangeTest {

    @Test
    public void test() {

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> requestEntity =
                RequestEntity
                        .get(URI.create("http://localhost:8080/resources/app/css/styles.css"))
                        .header("Range", "bytes=0-199,200-").build();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());


    }
}
