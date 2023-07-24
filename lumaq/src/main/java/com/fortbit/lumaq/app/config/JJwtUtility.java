package com.fortbit.lumaq.app.config;

import com.fortbit.lumaq.core.domain.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JJwtUtility {

    private String secret = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims.
     * These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param userAuth the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(UserAuth userAuth) {
        Claims claims = Jwts.claims().setSubject(userAuth.getName());
        claims.put("id", userAuth.getId());
//        claims.put("role", getListAsString(userAuth.getRoles()));
        claims.put("emailId", userAuth.getEmailId());
        return Jwts.builder().setClaims(claims).signWith(HS512, secret).compact();
    }

}
