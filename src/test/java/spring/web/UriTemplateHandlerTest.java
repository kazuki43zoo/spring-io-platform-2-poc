package spring.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriTemplateHandler;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class UriTemplateHandlerTest {

    @Test
    public void testWithDefault() {

        RestTemplate restTemplate = new RestTemplate();

        MockRestServiceServer.createServer(restTemplate).expect(requestTo("https://api.github.com/users/kazuki43zoo"))
                .andRespond(withSuccess("Hello world", MediaType.TEXT_PLAIN));

        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("api", "users/kazuki43zoo");
        String content = restTemplate.getForObject("https://api.github.com/{api}", String.class, urlVariables);

        assertThat(content, is("Hello world"));

    }

    @Test
    public void testWithParseIsTrue() {

        DefaultUriTemplateHandler handler = new DefaultUriTemplateHandler();
        handler.setParsePath(true);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(handler);

        MockRestServiceServer.createServer(restTemplate).expect(requestTo("https://api.github.com/users%2Fkazuki43zoo"))
                .andRespond(withSuccess("Hello world", MediaType.TEXT_PLAIN));

        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("api", "users/kazuki43zoo");
        String content = restTemplate.getForObject("https://api.github.com/{api}", String.class, urlVariables);

        assertThat(content, is("Hello world"));

    }

}
