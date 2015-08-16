package spring.web;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class OkHttpTest {

    @Test
    public void test() {

        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);

        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("username", "kazuki43zoo");
        String content = restTemplate.getForObject("https://api.github.com/users/{username}", String.class, urlVariables);
        System.out.println(content);

    }
}
