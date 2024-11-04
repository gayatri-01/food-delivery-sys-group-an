package com.group.an.authService.security;


import com.group.an.dataService.models.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    //Valid for 5 hours
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    public static final String ROLE = "role";

    public static final String USER_ID = "userId";

    // Use a constant or load from environment or configuration file
    private static final String SECRET_KEY = "s3cUr3K3y_1234567890abcdefghijklmnopqrstuvwxyz";

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    public Integer getUserIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(USER_ID, Integer.class);
    }
    public Role getRoleFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return Role.valueOf(claims.get(ROLE, String.class));
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Integer userId, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID,userId);
        claims.put(ROLE,role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
    }


    public Boolean validateToken(String token, Integer userIdFromRepository) {
        final Integer userIdFromToken = getUserIdFromToken(token);
        return (userIdFromToken.equals(userIdFromRepository) && !isTokenExpired(token));
    }

    public static Integer getUserIdFromAuthContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt(((UserDetails)authentication.getPrincipal()).getUsername());
    }

    public static Role getRoleFromAuthContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Role.valueOf(((SimpleGrantedAuthority)(authentication.getAuthorities().toArray()[0])).getAuthority().substring(5));
    }
}
