package spring.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("classpath:META-INF/spring/applicationContext.xml"),
        @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml"),
})
public class StreamingTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testBodyEmitter() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/streaming/bodyEmitter"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().string("")).andReturn();

        Thread.sleep(1500);

        assertThat(result.getResponse().getContentAsString(), containsString("Hello!"));
        assertThat(result.getResponse().getContentAsString(), containsString("Hello again!"));

    }

    @Test
    public void testSseEmitter() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/streaming/sseEmitter"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().contentType(MediaType.parseMediaType("text/event-stream")))
                .andExpect(
                        content().string("")).andReturn();

        Thread.sleep(1500);

        assertThat(result.getResponse().getContentAsString(), containsString("data:Hello!"));
        assertThat(result.getResponse().getContentAsString(), containsString("data:Hello again!"));

    }


    @Test
    public void testDirectBody() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/streaming/directBody"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().string("")).andReturn();

        Thread.sleep(1500);

        assertThat(result.getResponse().getContentAsString(), containsString("Hello!"));
        assertThat(result.getResponse().getContentAsString(), containsString("Hello again!"));

    }

}
