package site.lyc8503.chat.integration;

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

import static cn.dev33.satoken.secure.BCrypt.checkpw;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static site.lyc8503.chat.integration.Util.bizError;
import static site.lyc8503.chat.integration.Util.success;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class UserTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private UserDao dao;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testPostUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user1",
                            "password": "PASSword1",
                            "email": "1@example.com",
                            "nickname": "1234"
                        }
                        """);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(success(201));

        UserEntity user = dao.findByUsername("user1");
        assertNotNull(user);

        assertEquals("user1", user.getUsername());
        assertTrue(user.getPasswordHash().startsWith("$2a$"));
        assertTrue(checkpw("PASSword1", user.getPasswordHash()));
        assertEquals("1@example.com", user.getEmail());
        assertEquals("1234", user.getNickname());
    }

    @Test
    void testPostUserWithDuplicateUsername() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user1",
                            "password": "PASSword1",
                            "email": "1@example.com",
                            "nickname": "1234"
                        }
                        """);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(success(201));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(bizError(ErrorType.USERNAME_EXISTS));
    }
}
