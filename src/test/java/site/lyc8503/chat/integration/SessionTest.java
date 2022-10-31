package site.lyc8503.chat.integration;


import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
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
import site.lyc8503.chat.dao.UserDao;
import site.lyc8503.chat.exception.ErrorType;
import site.lyc8503.chat.pojo.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static site.lyc8503.chat.integration.Util.bizError;
import static site.lyc8503.chat.integration.Util.success;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class SessionTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private UserDao dao;

    String sessionToken;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        // Create test user
        UserEntity user = UserEntity.builder()
                .username("user1")
                .passwordHash(BCrypt.hashpw("PASSword1"))
                .email("1@example.com")
                .nickname("nick")
                .build();
        dao.save(user);
        UserEntity user2 = UserEntity.builder()
                .username("user2")
                .passwordHash(BCrypt.hashpw("PASSword2"))
                .email("2@example.com")
                .nickname("nick2")
                .build();
        dao.save(user2);

        StpUtil.login("user2");
        sessionToken = StpUtil.getTokenValue();
        assertNotNull(sessionToken);
    }

    @Test
    public void postSession() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user1",
                            "password": "PASSword1"
                        }
                        """);

        mockMvc.perform(request)
                .andExpect(success(201))
                .andExpect(jsonPath("$.data.token").isString())
                .andExpect(jsonPath("$.data.expires").isNumber());
    }

    @Test
    public void postSessionWrongUsername() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user2",
                            "password": "PASSword1"
                        }
                        """);

        mockMvc.perform(request)
                .andExpect(bizError(ErrorType.INVALID_CREDENTIAL))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void postSessionWrongPassword() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user1",
                            "password": "PASSword2"
                        }
                        """);

        mockMvc.perform(request)
                .andExpect(bizError(ErrorType.INVALID_CREDENTIAL))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void getSession() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/session")
                .header("token", sessionToken);

        mockMvc.perform(request)
                .andExpect(success())
                .andExpect(jsonPath("$.data.login").value(true))
                .andExpect(jsonPath("$.data.username").value("user2"));
    }

    @Test
    public void getSessionNotLogin() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/session")
                .header("token", "A_INVALID_OR_EXPIRED_TOKEN");

        mockMvc.perform(request)
                .andExpect(success())
                .andExpect(jsonPath("$.data.login").value(false))
                .andExpect(jsonPath("$.data.username").doesNotExist());
    }

    @Test
    public void deleteSession() throws Exception {
        RequestBuilder getRequest = MockMvcRequestBuilders.get("/session")
                .header("token", sessionToken);

        RequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/session")
                .header("token", sessionToken);

        mockMvc.perform(getRequest)
                .andExpect(success())
                .andExpect(jsonPath("$.data.login").value(true));

        mockMvc.perform(deleteRequest)
                .andExpect(success());

        mockMvc.perform(getRequest)
                .andExpect(success())
                .andExpect(jsonPath("$.data.login").value(false));
    }

    @Test
    public void deleteSessionNotLogin() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/session")
                .header("token", "A_INVALID_OR_EXPIRED_TOKEN");

        mockMvc.perform(request)
                .andExpect(bizError(ErrorType.NOT_LOGIN));
    }
}
