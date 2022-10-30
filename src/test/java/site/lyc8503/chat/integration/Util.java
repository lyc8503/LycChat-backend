package site.lyc8503.chat.integration;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.test.web.servlet.ResultMatcher;
import site.lyc8503.chat.exception.ErrorType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Util {

    public static ResultMatcher bizError(ErrorType error) {
        return bizError(error, false);
    }

    public static ResultMatcher bizError(ErrorType error, boolean allowData) {
        return result -> {
            int status = result.getResponse().getStatus();
            assertEquals(error.getHttpCode(), status);

            String res = result.getResponse().getContentAsString();
            JSONObject json = new JSONObject(res);
            int code = json.getInt("code");
            assertEquals(error.getCode(), code);

            if (!allowData) {
                assertThrows(JSONException.class, () -> json.get("data"));
            }
        };
    }
}
