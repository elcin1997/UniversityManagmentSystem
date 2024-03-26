package org.example.universitymanagementsystem.controller;

import lombok.AllArgsConstructor;
import org.example.universitymanagementsystem.dto.AuthenticationRequestDTO;
import org.example.universitymanagementsystem.dto.AuthenticationResponseDTO;
import org.example.universitymanagementsystem.dto.RegisterRequestDTO;
import org.example.universitymanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
