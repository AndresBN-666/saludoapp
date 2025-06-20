package com.ejemplo.saludoapp.controller;

import com.ejemplo.saludoapp.DTO.login.LoginRequest;
import com.ejemplo.saludoapp.DTO.login.LoginResponse;
import com.ejemplo.saludoapp.seguridad.jwt.JwtService;
import com.ejemplo.saludoapp.serviceImpl.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Autenticacion de Usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getClave())
        );

        //Si pasa la autenticacion genera el token
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token = jwtService.generarToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
