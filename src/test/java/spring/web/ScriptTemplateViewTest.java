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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("classpath:META-INF/spring/applicationContext.xml"),
        @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml"),
})
public class ScriptTemplateViewTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void test() throws Exception {
        this.mockMvc.perform(
                get("/script-template"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().string(
                                "<html>\n" +
                                        "<head>\n" +
                                        "    <title>Sample title</title>\n" +
                                        "</head>\n" +
                                        "<body>\n" +
                                        "<p>Sample body</p>\n" +
                                        "</body>\n" +
                                        "</html>\n"));
    }


}
