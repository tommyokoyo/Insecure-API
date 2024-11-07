package com.eclectics.security.insecureapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JWTFilter JWTFilter;

    @Autowired
    public SecurityConfig(JWTFilter jwtFilter) {
        this.JWTFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/get-all-users-vuln").permitAll()
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/IDOR/**").authenticated()
                        .requestMatchers("/api/mass-assignment/**").authenticated()
                        .requestMatchers("/api/data-expose/**").authenticated()
                        .anyRequest().permitAll())
                .logout(logout -> logout.permitAll());

        // JWT filter
        http.addFilterBefore(JWTFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
