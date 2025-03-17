package com.app.api.service.implement;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String createToken(Authentication authentication, Long expired, String scope) {
        Instant now = Instant.now();
        if(scope == null){
            scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
        }
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expired))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("username", authentication.getName())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
    public Jwt getJwt(String token){
        return this.jwtDecoder.decode(token);
    }

    public boolean validateToken(String token){
        try {
            this.jwtDecoder.decode(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public String getClaim(String token, String claim){
        return this.getJwt(token).getClaimAsString(claim);
    }

}
