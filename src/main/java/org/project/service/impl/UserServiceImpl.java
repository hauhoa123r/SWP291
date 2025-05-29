package org.project.service.impl;

import org.project.entity.UserEntity;
import org.project.model.dto.UserLoginDTO;
import org.project.model.response.UserLoginResponse;
import org.project.repository.UserRepository;
import org.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.project.converter.toConverterLogin;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepositoryImpl;

    @Autowired
    private toConverterLogin toConverterLoginResponse;

    @Override
    public UserLoginResponse isLogin(UserLoginDTO userLoginDTO) {
        UserEntity userEntity = userRepositoryImpl.findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        UserLoginResponse userLoginResponse = toConverterLoginResponse.toConverterUserLoginResponse(userEntity);
        return userLoginResponse;
    }


    public static void main(String[] args) {
        String str = "hehe,huhu,haha,hoho";

        String[] arr = str.split(",");
        int b = 10;
        int c = 20;
        int a = 10;
    }
}
