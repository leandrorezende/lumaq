package com.fortbit.lumaq.app;

import com.fortbit.lumaq.core.domain.UserAuth;
import com.fortbit.lumaq.infrastructure.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("userauth")
public class UserAuthController {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @GetMapping
    public List<UserAuth> getUsersAuth() {
        return userAuthRepository.findAll();
    }

}
