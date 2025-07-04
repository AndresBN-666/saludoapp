package com.ejemplo.saludoapp.seguridad.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String SECRET_KEY= "mi-clave-super-secreta-y-larga-para-jwt123456789";

    private Key getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generarToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        claims.put("roles", authorities.stream()
                .map(GrantedAuthority ::  getAuthority)
                .collect(Collectors.toList())
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean esTokenValido(String token, UserDetails userDetails){
        final String username = extraerUsername(token);
        return username.equals(userDetails.getUsername()) && !estaExpirado(token);
    }

    public boolean estaExpirado(String token){
        Date fechaExpiracion= Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return fechaExpiracion.before(new Date());
    }
}
