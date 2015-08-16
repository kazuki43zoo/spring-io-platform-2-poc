package spring.json;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Ignore
public class JsonPrefixTest {

    @Test
    public void test() {

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> requestEntity =
                RequestEntity
                        .get(URI.create("http://localhost:8080/json/message/msg001"))
                        .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());


        assertThat(responseEntity.getBody(), is(")]}', {\"message\":\"msg001\"}"));


    }

}
