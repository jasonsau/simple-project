package com.app.api.config.fiter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter{

    private final JwtDecoder jwtDecoder;

    public JwtValidationFilter(AuthenticationManager authenticationManager, JwtDecoder jwtDecoder) {
        super(authenticationManager);
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }
        Jwt jwt;
        String token = header.replace("Bearer ", "");
        try {
            jwt = jwtDecoder.decode(token);
            String username = jwt.getClaimAsString("username");
            String [] scopes = jwt.getClaimAsString("scope").split(",");
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(scopes).map(SimpleGrantedAuthority::new).toList();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } catch(JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
        }

    }

}
