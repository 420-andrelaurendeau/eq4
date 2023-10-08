package com.equipe4.audace.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/students/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.STUDENT.name())
                .requestMatchers("/employers/{id}").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.EMPLOYER.name(), Authorities.MANAGER.name(), Authorities.STUDENT.name())
                .requestMatchers("/employers/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.EMPLOYER.name())
                .requestMatchers("/managers/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.MANAGER.name())
                .requestMatchers("/users/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.USER.name())
            );

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
