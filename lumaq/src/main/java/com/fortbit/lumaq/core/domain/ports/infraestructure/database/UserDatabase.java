package com.fortbit.lumaq.core.domain.ports.infraestructure.database;

import com.fortbit.lumaq.core.domain.UserAuth;

import java.util.Optional;

public interface UserDatabase {

    boolean authenticate(String userId, String password);

    Optional<UserAuth> findById(String userId);
}
