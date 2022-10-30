package site.lyc8503.chat.integration.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import site.lyc8503.chat.exception.ErrorType;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.lyc8503.chat.integration.Util.bizError;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class CommonTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testBadRequestInvalidBody() throws Exception {
        String data = "qwe";
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data);

        mockMvc.perform(request).andDo(print()).andExpect(bizError(ErrorType.BAD_REQUEST));
    }

    @Test
    void testBadRequestInvalidContentType() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.TEXT_PLAIN)
                .content("{}");

        mockMvc.perform(request).andDo(print()).andExpect(bizError(ErrorType.BAD_REQUEST));
    }

    @Test
    void testAnnotationConstraints() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        mockMvc.perform(request).andDo(print()).andExpect(bizError(ErrorType.ILLEGAL_ARGUMENTS));
    }

    @Test
    void testNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/invalid_path");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}
