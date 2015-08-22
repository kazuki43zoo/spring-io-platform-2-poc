package spring.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("classpath:META-INF/spring/applicationContext.xml"),
        @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml"),
})
public class CrosTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGlobalWithAllowedOrigin() throws Exception {
        this.mockMvc.perform(
                get("/cros/global/message")
                        .param("messageId", "msg001")
                        .header("Origin", "http://spring.example.jp:8080"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().string("msg001"))
                .andExpect(
                        header().string("Access-Control-Allow-Origin", "http://spring.example.jp:8080"))
                .andExpect(
                        header().string("Access-Control-Allow-Credentials", "true"));
    }

    @Test
    public void testGlobalWithNotAllowedOrigin() throws Exception {
        this.mockMvc.perform(
                get("/cros/global/message")
                        .param("messageId", "msg001")
                        .header("Origin", "http://spring.example.com:8080"))
                .andExpect(
                        status().isForbidden())
                .andExpect(
                        content().string("Invalid CORS request"));
    }


    @Test
    public void testLocalWithAllowedOrigin() throws Exception {
        this.mockMvc.perform(
                get("/cros/local/message")
                        .param("messageId", "msg001")
                        .header("Origin", "http://spring.example.com:8080"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().string("msg001"))
                .andExpect(
                        header().string("Access-Control-Allow-Origin", "http://spring.example.com:8080"))
                .andExpect(
                        header().string("Access-Control-Allow-Credentials", "true"));
    }


    @Test
    public void testLocalWithNotAllowedOrigin() throws Exception {
        this.mockMvc.perform(
                get("/cros/local/message")
                        .param("messageId", "msg001")
                        .header("Origin", "http://spring.example.jp:8080"))
                .andExpect(
                        status().isForbidden())
                .andExpect(
                        content().string("Invalid CORS request"));
    }

}
