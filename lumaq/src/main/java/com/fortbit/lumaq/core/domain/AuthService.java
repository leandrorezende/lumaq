package com.fortbit.lumaq.core.domain;

import com.fortbit.lumaq.app.config.JJwtUtility;
import com.fortbit.lumaq.core.domain.ports.infraestructure.database.UserDatabase;
import com.fortbit.lumaq.infrastructure.UserAuthRepository;
import com.fortbit.lumaq.infrastructure.database.repository.UserRepository;
import com.fortbit.lumaq.infrastructure.database.repository.jpa.UserJPA;
import lombok.Getter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static lombok.AccessLevel.PACKAGE;
import static org.slf4j.LoggerFactory.getLogger;

@Component
@Getter(PACKAGE)
public class AuthService {

    private final Logger log = getLogger(AuthService.class);

    @Value("${auth.client.id:web-client}")
    private String authClientId;

    @Value("${auth.client.secret:secret}")
    private String authClientSecret;

    private final JJwtUtility jwtUtilBean;

    private final UserDatabase userDatabase;

    @Autowired
    public AuthService(JJwtUtility jwtUtilBean, UserDatabase userDatabase) {
        this.jwtUtilBean = jwtUtilBean;
        this.userDatabase = userDatabase;
    }

    /**
     * @param basicAuthHeader Authorization: Basic base64(user:pass)
     * @return true if client is authenticated
     */
    public boolean authenticateClient(String basicAuthHeader) {
        String base64Credentials = basicAuthHeader.substring("Basic".length()).trim();
        byte[] credDecoded = getDecoder().decode(base64Credentials);
        String userColonPass = new String(credDecoded, UTF_8);
        final String[] credentials = userColonPass.split(":", 2);
        if (credentials[0].equals(authClientId)) {
            if (credentials[1].equals(authClientSecret)) {
                getLog().debug("Client validated as {}", authClientId);
                return true;
            }
        }
        getLog().warn("Incorrect client credentials {}", userColonPass);
        return false;
    }

    public OAuthToken getOAuthToken(String userId, String password) {

        if (!getUserDatabase().authenticate(userId, password)) {
            getLog().info("User log in FAILED; userId={}", userId);
            return null;
        }

        UserAuth userAuth = getUserDatabase().findById(userId).get();
//        List<String> userRoleIds = getAuthBean().getUserRole(userId);
//        userAuth.setRoles(userRoleIds);
        getLog().info("User log in successful; context={}", userAuth);

        String token = getJwtUtilBean().generateToken(userAuth);
        OAuthToken authToken = new OAuthToken(token);
        getLog().debug("Generated auth token; userId={}; context={}", userId, authToken);
        return authToken;
    }

}
