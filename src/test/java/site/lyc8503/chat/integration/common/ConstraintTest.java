package site.lyc8503.chat.integration.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import site.lyc8503.chat.exception.ErrorType;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static site.lyc8503.chat.integration.Util.bizError;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ConstraintTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * Just use POST users as an example
     */
    @Test
    void testNotNull() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        MvcResult result = mockMvc.perform(request).andDo(print()).andExpect(bizError(ErrorType.ILLEGAL_ARGUMENTS)).andReturn();
        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("must not be null"));
    }

    @Test
    void testSize() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user1",
                            "password": "PASSword1",
                            "nickname": "n",
                            "email": "user1@example.com"
                        }
                        """);

        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(bizError(ErrorType.ILLEGAL_ARGUMENTS))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("长度必须"));
    }

    @Test
    void testEmail() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user2",
                            "password": "PASSword1",
                            "nickname": "nick",
                            "email": "@example.com"
                        }
                        """);

        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(bizError(ErrorType.ILLEGAL_ARGUMENTS))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("邮箱格式不正确"));
    }

    @Test
    void testPattern() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user3",
                            "password": "password1",
                            "nickname": "nick",
                            "email": "user3@example.com"
                        }
                        """);

        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(bizError(ErrorType.ILLEGAL_ARGUMENTS))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("密码未达到复杂性要求"));
    }

    @Test
    void testPattern2() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user4",
                            "password": "密码测试PASSword1",
                            "nickname": "nick",
                            "email": "user4@example.com"
                        }
                        """);

        MvcResult result = mockMvc.perform(request)
                .andDo(print())
                .andExpect(bizError(ErrorType.ILLEGAL_ARGUMENTS))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("只能包含字母,数字和符号"));
    }
}
