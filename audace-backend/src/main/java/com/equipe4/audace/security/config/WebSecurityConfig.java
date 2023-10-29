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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .requestMatchers("/users/sessions/**").permitAll()
                .requestMatchers("/students/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.STUDENT.name())
                .requestMatchers("/employers/{id}").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.EMPLOYER.name(), Authorities.MANAGER.name(), Authorities.STUDENT.name())
                .requestMatchers("/employers/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.EMPLOYER.name())
                .requestMatchers("/managers/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.MANAGER.name())
                .requestMatchers("/users/**").hasAnyAuthority(Authorities.ADMIN.name(), Authorities.USER.name())


            );

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors(cors -> corsConfigurationSource());
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
