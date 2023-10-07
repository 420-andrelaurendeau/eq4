package com.equipe4.audace.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.User;
import com.equipe4.audace.security.jwt.TimedJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtManipulator {
    private final String issuer;
    private final Algorithm signingAlgorithm;
    private final long expirationMs;

    @Autowired
    public JwtManipulator(
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expirationMs}") int expirationMs
    ) {
        this.issuer = issuer;
        this.expirationMs = expirationMs;
        this.signingAlgorithm = Algorithm.HMAC256(secret);
    }

    public TimedJwt generateToken(User user) {
        return new TimedJwt(
                JWT.create()
                        .withSubject(user.getEmail())
                        .withIssuedAt(new Date())
                        .withClaim("id", user.getId())
                        .withClaim("email", user.getEmail())
                        .withClaim("role", determineRole(user))
                        .withExpiresAt(new Date(new Date().getTime() + expirationMs))
                        .withIssuer(issuer)
                        .sign(signingAlgorithm),
                expirationMs
        );
    }

    private String determineRole(User user) {
        if (user instanceof Student)
            return "STUDENT";
        if (user instanceof Employer)
            return "EMPLOYER";
        if (user instanceof Manager)
            return "MANAGER";

        return "USER";
    }

    public DecodedJWT decodeToken(String token) {
        return JWT.require(signingAlgorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
    }
}
