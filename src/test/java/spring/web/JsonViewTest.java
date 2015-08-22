package spring.web;

import com.example.app.bodyadvice.BodyAdviceRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("classpath:META-INF/spring/applicationContext.xml"),
        @ContextConfiguration("classpath:META-INF/spring/spring-mvc.xml"),
})
public class JsonViewTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Inject
    BodyAdviceRestController controller;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        controller.getAccounts().stream().forEach(resource -> {
            controller.deleteAccount(resource.getId());
        });

    }


    @Test
    public void getFormat() throws Exception {

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/format"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(getFileContent("spring/web/JsonViewTest/getFormat-expected.json")));

    }

    @Test
    public void postAccount() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated())
                .andExpect(
                        content().json(getFileContent("spring/web/JsonViewTest/postAccount-expected.json")));

    }

    @Test
    public void getAccount() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/1"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(getFileContent("spring/web/JsonViewTest/getAccount-expected.json")));

    }


    @Test
    public void getPersonName() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/1/personName"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(getFileContent("spring/web/JsonViewTest/getPersonName-expected.json")));

    }


    @Test
    public void getStreetAddress() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/1/streetAddress"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(getFileContent("spring/web/JsonViewTest/getStreetAddress-expected.json")));

    }

    @Test
    public void getMailAddress() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/1/mailAddress"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(getFileContent("spring/web/JsonViewTest/getMailAddress-expected.json")));

    }


    @Test
    public void putAccount() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());


        String requestContent = getFileContent("spring/web/JsonViewTest/putAccount-request.json");
        String expectedContent = requestContent;

        this.mockMvc.perform(
                put("/rest/bodyadvice/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(expectedContent));


    }

    @Test
    public void putPersonName() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());


        String requestContent = getFileContent("spring/web/JsonViewTest/putAccount-request.json");
        String expectedContent = getFileContent("spring/web/JsonViewTest/putPersonName-expected.json");

        this.mockMvc.perform(
                put("/rest/bodyadvice/accounts/1/personName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(
                        status().isOk());

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/1"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(expectedContent));

    }


    @Test
    public void putStreetAddress() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());


        String requestContent = getFileContent("spring/web/JsonViewTest/putAccount-request.json");
        String expectedContent = getFileContent("spring/web/JsonViewTest/putStreetAddress-expected.json");

        this.mockMvc.perform(
                put("/rest/bodyadvice/accounts/1/streetAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(
                        status().isOk());

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/1"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(expectedContent));

    }


    @Test
    public void putMailAddress() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            @Override
            public String generate() {
                return "1";
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());


        String requestContent = getFileContent("spring/web/JsonViewTest/putAccount-request.json");
        String expectedContent = getFileContent("spring/web/JsonViewTest/putMailAddress-expected.json");

        this.mockMvc.perform(
                put("/rest/bodyadvice/accounts/1/mailAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(
                        status().isOk());

        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts/1"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(expectedContent));

    }

    @Test
    public void getAccounts() throws Exception {
        controller.setIdGenerator(new BodyAdviceRestController.IdGenerator() {
            int idCounter = 0;
            @Override
            public String generate() {
                return String.valueOf(++idCounter);
            }
        });

        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());
        this.mockMvc.perform(
                post("/rest/bodyadvice/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getFileContent("spring/web/JsonViewTest/postAccount-request.json")))
                .andExpect(
                        status().isCreated());


        String expectedContent = getFileContent("spring/web/JsonViewTest/getAccounts-expected.json");


        this.mockMvc.perform(
                get("/rest/bodyadvice/accounts"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        content().json(expectedContent));

    }

    private String getFileContent(String file) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(file).getInputStream(), StandardCharsets.UTF_8);
    }

}
