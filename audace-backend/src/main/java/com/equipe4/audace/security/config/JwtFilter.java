package com.equipe4.audace.security.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.equipe4.audace.dto.UserDTO;
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
import java.util.*;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtManipulator jwtManipulator;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = jwtManipulator.getJwt(request);

        if (jwt.isPresent()) {
            String token = jwt.get();
            DecodedJWT decodedJWT = jwtManipulator.decodeToken(token);
            Optional<User> optionalUser = userRepository.findByEmail(decodedJWT.getSubject());

            UserDTO userDTO;

            if (optionalUser.isPresent()) {
                userDTO = optionalUser.get().toDTO();
            } else {
                handleInvalidToken(request, response, filterChain);
                return;
            }

            List<String> authorities = jwtManipulator.determineAuthorities(userDTO);

            if (!isTokenValid(decodedJWT, authorities)) {
                handleInvalidToken(request, response, filterChain);
                return;
            }

            List<GrantedAuthority> grantedAuthorities = convertAuthorities(authorities);

            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .authorities(grantedAuthorities)
                    .build();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(DecodedJWT decodedJWT, List<String> authorities) {
        return !isTokenExpired(decodedJWT) && doAuthoritiesMatch(decodedJWT, authorities);
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().before(new Date());
    }

    private boolean doAuthoritiesMatch(DecodedJWT decodedJWT, List<String> authorities) {
        return new HashSet<>(decodedJWT.getClaim("authorities").asList(String.class)).containsAll(authorities);
    }

    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        filterChain.doFilter(request, response);
    }

    private List<GrantedAuthority> convertAuthorities(List<String> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities)
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        return grantedAuthorities;
    }
}
