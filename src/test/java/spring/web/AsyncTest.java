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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("classpath:META-INF/spring/applicationContext.xml"),
        @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml"),
})
public class AsyncTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCallable() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/async/callable")
                        .param("wait", "1"))
                .andExpect(
                        status().isOk())
                .andReturn();

        assertThat(result.getAsyncResult(1500), is("welcome/home"));
        assertThat(result.getModelAndView(),nullValue());

    }

    @Test
    public void testDeferredResult() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/async/deferred")
                        .param("wait", "1"))
                .andExpect(
                        status().isOk())
                .andReturn();

        assertThat(result.getAsyncResult(1500), is("welcome/home"));
        assertThat(result.getModelAndView(), nullValue());

    }


    @Test
    public void testListenableFuture() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/async/listenableFuture")
                        .param("wait", "1"))
                .andExpect(
                        status().isOk())
                .andReturn();

        assertThat(result.getAsyncResult(1500), is("welcome/home"));
        assertThat(result.getModelAndView(),nullValue());

    }

    @Test
    public void testCompletableFuture() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/async/completableFuture")
                        .param("wait", "1"))
                .andExpect(
                        status().isOk())
                .andReturn();

        assertThat(result.getAsyncResult(1500), is("welcome/home"));
        assertThat(result.getModelAndView(),nullValue());

    }
}
