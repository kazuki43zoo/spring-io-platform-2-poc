package spring.web;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class UriTemplateHandlerTest {

    @Test
    public void testWithDefault() {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("api", "users/kazuki43zoo");
        String content = restTemplate.getForObject("https://api.github.com/{api}", String.class, urlVariables);
        System.out.println(content);

    }

    @Test(expected = HttpClientErrorException.class)
    public void testWithParseIsTrue() {

        DefaultUriTemplateHandler handler = new DefaultUriTemplateHandler();
        handler.setParsePath(true);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(handler);

        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("api", "users/kazuki43zoo");
        String content = restTemplate.getForObject("https://api.github.com/{api}", String.class, urlVariables);
        System.out.println(content);

    }

}
