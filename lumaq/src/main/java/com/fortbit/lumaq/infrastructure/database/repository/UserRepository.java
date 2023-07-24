package com.fortbit.lumaq.infrastructure.database.repository;

import com.fortbit.lumaq.core.domain.UserAuth;
import com.fortbit.lumaq.core.domain.ports.infraestructure.database.UserDatabase;
import com.fortbit.lumaq.infrastructure.database.repository.jpa.UserJPA;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;

@Repository
@Getter(PACKAGE)
public class UserRepository implements UserDatabase {

    private final UserJPA userJPA;

    @Autowired
    public UserRepository(UserJPA userJPA) {
        this.userJPA = userJPA;
    }

    public boolean authenticate(String userId, String password) {
        return getUserJPA().authenticate(userId, password);
    }

    public Optional<UserAuth> findById(String userId) {
        return getUserJPA().findById(userId);
    }

}