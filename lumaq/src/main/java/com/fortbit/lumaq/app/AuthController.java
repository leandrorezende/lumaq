package com.fortbit.lumaq.app;

import com.fortbit.lumaq.core.domain.AuthService;
import com.fortbit.lumaq.core.domain.OAuthToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.fortbit.lumaq.app.config.Resources.AUTHORIZATION;
import static com.fortbit.lumaq.app.config.Resources.AUTH_TOKEN;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static lombok.AccessLevel.PACKAGE;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.CacheControl.maxAge;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * AuthService provides rest interface for authentication and authorization
 * This class can be configured to use LDAP or Database as the user auth storage
 * It only supports OAuth2 grant type of 'Password Credentials'
 */
@RestController
@RequestMapping(AUTHORIZATION)
@Getter(PACKAGE)
public class AuthController {

    private final Logger log = getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * This method is used for generating auth token by posting user cred as json
     * This method is open to all roles
     *
     * @param userCredentials json representation of UserCredentials object
     * @return auth token in http response body
     */
    @PostMapping(path = AUTH_TOKEN, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postUserCred(
            HttpServletRequest httpServletRequest, @RequestBody UserCredentials userCredentials
    ) {
        getLog().info("Received user authentication; context={}", userCredentials);

        if (!authenticateClient(httpServletRequest)) {
            return status(UNAUTHORIZED).body("Invalid Client Credentials");
        }
        OAuthToken authToken = getAuthService().getOAuthToken(userCredentials.getId(), userCredentials.getPassword());
        return createTokenResponse(userCredentials.getId(), authToken);
    }

    private boolean authenticateClient(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        getLog().debug("Client authorization header: {}", authHeader);
        if (nonNull(authHeader) && authHeader.toLowerCase().startsWith("basic")) {
            return getAuthService().authenticateClient(authHeader);
        }
        getLog().warn("No authorization header present for basic authentication of the client");
        return false;
    }

    private ResponseEntity<OAuthToken> createTokenResponse(String userId, OAuthToken authToken) {
        if (isNull(authToken)) {
            getLog().info("User log in FAILED; userId={}; context={}", userId, null);
            return new ResponseEntity<>(FORBIDDEN);
        }
        getLog().info("User log in SUCCESSFUL; userId={}; context={}", userId, authToken);
        CacheControl cc = maxAge(3600, SECONDS);
        return ok().cacheControl(cc).header("Pragma", "no-cache").body(authToken);
    }

}
