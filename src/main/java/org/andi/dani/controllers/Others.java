package org.andi.dani.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/others")
public class Others {
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public Mono<String> execute() {
        return Mono.just("content");
    }
}
