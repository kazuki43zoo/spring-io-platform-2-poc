package spring.web;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;

@Ignore
public class RestTemplateOkHttpTest {

    @Test
    public void test() {

        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        Map<String, Object> urlVariables = new HashMap<>();
        urlVariables.put("username", "kazuki43zoo");
        String content = restTemplate.getForObject("https://api.github.com/users/{username}", String.class, urlVariables);
        System.out.println(content);

    }

    @Test
    public void testGet() {


        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        String content = restTemplate.getForObject("http://localhost:8080/rest/test", String.class);

        assertThat(content, is("get by test"));

    }

    @Test
    public void testPost() {

        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        String content = restTemplate.postForObject("http://localhost:8080/rest", null, String.class);

        assertThat(content, is(startsWith("post with ")));

    }

    @Test
    public void testPut() {

        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        String content = restTemplate.exchange(
                RequestEntity.put(URI.create("http://localhost:8080/rest/test")).build(),
                String.class).getBody();

        assertThat(content, is("put by test"));

    }

    @Test
    public void testPatch() {

        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        String content = restTemplate.exchange(
                RequestEntity.patch(URI.create("http://localhost:8080/rest/test")).build(),
                String.class).getBody();

        assertThat(content, is("patch by test"));

    }

    @Test
    public void testDelete() {

        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        String content = restTemplate.exchange(
                RequestEntity.delete(URI.create("http://localhost:8080/rest/test")).build(),
                String.class).getBody();

        assertThat(content, is("delete by test"));

    }

    @Test
    public void testOptions() {

        OkHttpClientHttpRequestFactory factory = new OkHttpClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders httpHeaders = restTemplate.exchange(
                RequestEntity.options(URI.create("http://localhost:8080/rest/test")).build(),
                String.class).getHeaders();

        assertThat(httpHeaders.getAllow(), is(containsInAnyOrder(
                HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST, HttpMethod.PUT,
                HttpMethod.DELETE, HttpMethod.TRACE, HttpMethod.OPTIONS, HttpMethod.PATCH)));
    }


}
