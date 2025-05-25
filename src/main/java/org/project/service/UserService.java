package org.project.service;

import org.project.model.dto.UserLoginDTO;
import org.project.model.response.UserLoginResponse;

public interface UserService {
    UserLoginResponse isLogin(UserLoginDTO userLoginDTO);
}
