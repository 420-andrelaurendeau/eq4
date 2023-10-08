package com.equipe4.audace.security.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.equipe4.audace.model.User;
import com.equipe4.audace.repository.UserRepository;
import com.equipe4.audace.utils.JwtManipulator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtManipulator jwtManipulator;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = jwtManipulator.getJwt(request);

        if (jwt.isPresent()) {
            String token = jwt.get();
            DecodedJWT decodedJWT = jwtManipulator.decodeToken(token);
            User user = userRepository.findByEmail(decodedJWT.getSubject()).orElseThrow();

            String role = jwtManipulator.determineRole(user);

            if (!isTokenValid(decodedJWT, role))
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(getAuthorities(role))
                    .build();

            List<GrantedAuthority> authorities = getAuthorities(role);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(DecodedJWT decodedJWT, String role) {
        return !isTokenExpired(decodedJWT) && doesRoleMatch(decodedJWT, role);
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().before(new Date());
    }

    private boolean doesRoleMatch(DecodedJWT decodedJWT, String role) {
        return decodedJWT.getClaim("role").asString().equals(role);
    }

    private List<GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
