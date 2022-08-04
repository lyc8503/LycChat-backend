package site.lyc8503.chat.service;

import site.lyc8503.chat.model.CommonResponse;
import site.lyc8503.chat.model.LoginResult;

public interface UserService {

    public CommonResponse<LoginResult> login();


}
