package org.project.api;

import org.project.model.dto.UserLoginDTO;
import org.project.model.response.UserLoginResponse;
import org.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    UserService userServiceImpl;

    @PostMapping("/login")
    public UserLoginResponse handleSubmit(@ModelAttribute UserLoginDTO userLoginDTO) {
        UserLoginResponse userLoginResponse = userServiceImpl.isLogin( userLoginDTO);
        return userLoginResponse;
    }
}
//git add .
//git commit -m "3 layer"
//git push origin main