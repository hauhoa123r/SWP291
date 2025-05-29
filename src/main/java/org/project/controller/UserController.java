package org.project.controller;

import lombok.RequiredArgsConstructor;
import org.project.config.ModelMapperConfig;
import org.project.entity.UserEntity;
import org.project.model.response.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("")
    public String hello(Model model) {
        return "index";
    }
}
