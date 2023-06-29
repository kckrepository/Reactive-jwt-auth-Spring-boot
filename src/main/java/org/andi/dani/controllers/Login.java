package org.andi.dani.controllers;

import lombok.AllArgsConstructor;
import org.andi.dani.security.JwtUtils;
import org.andi.dani.services.UserDetailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class Login {
    private final UserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping
    public Mono<String> execute() {
        return Mono.fromSupplier(() -> {
            try {
                return jwtUtils.generateJwtToken("dani");
            }
            catch (Exception exception) {
                return "";
            }
        });
    }
}
