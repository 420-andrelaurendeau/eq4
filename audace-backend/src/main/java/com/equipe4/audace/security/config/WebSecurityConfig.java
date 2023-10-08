package com.equipe4.audace.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/students/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.STUDENT.name())
                .requestMatchers("/employers/{id}").hasAnyAuthority(Roles.ADMIN.name(), Roles.EMPLOYER.name(), Roles.MANAGER.name(), Roles.STUDENT.name())
                .requestMatchers("/employers/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.EMPLOYER.name())
                .requestMatchers("/managers/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.MANAGER.name())
                .requestMatchers("/users/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.USER.name())
            );

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
