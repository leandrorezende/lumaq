package com.fortbit.lumaq.infrastructure.database.repository.jpa;

import com.fortbit.lumaq.core.domain.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJPA extends JpaRepository<UserAuth, String> {

    @Query("select count(c) > 0 from UserAuth c where c.id = :userId and c.password = :password")
    boolean authenticate(@Param("userId") String userId, @Param("password") String password);

}
