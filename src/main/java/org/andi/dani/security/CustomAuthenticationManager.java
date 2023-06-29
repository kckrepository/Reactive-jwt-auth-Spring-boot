package org.andi.dani.security;

import lombok.AllArgsConstructor;
import org.andi.dani.services.UserDetailService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtils jwtUtils;
    private final UserDetailService userDetailService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication.getCredentials().toString())
                .map(credential -> jwtUtils.validateJwtToken(credential))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    try {
                        return jwtUtils.getUsernameFromJwtToken(authentication
                                .getCredentials()
                                .toString());
                    }
                    catch (Exception e) {
                        return "";
                    }
                })
                .flatMap(username -> userDetailService.loadUserByUsername(username))
                .switchIfEmpty(Mono.empty())
                .map(userDetails -> {
                    return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                });
    }
}
