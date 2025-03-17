package com.app.api.config.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.api.config.fiter.JwtValidationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JpaUserDetails jpaUserDetails;
    private final PasswordEncoder passwordEncoder;
    private final JwtDecoder jwtDecoder;
    @Value("${app.cors}")
    String[] arrayCors;

    @Bean
    SecurityFilterChain securityFilterChaing(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf((crsf) -> crsf.disable())
        .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth 
            .requestMatchers("/auth/register", "/auth/login").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(formLogin -> formLogin.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilter(new JwtValidationFilter(authenticationManager(jpaUserDetails, passwordEncoder), jwtDecoder))
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(jpaUserDetails);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(
                List.of("Authorization","Content-Type", "Accept", "Origin", "X-Requested-With", "X-Frame-Options"));
        corsConfiguration.setAllowedOrigins(List.of(arrayCors));
        List<String> list = List.of(arrayCors);
        for (String s : list) {
            System.out.println(s + " Lista de origenes");
        }
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
