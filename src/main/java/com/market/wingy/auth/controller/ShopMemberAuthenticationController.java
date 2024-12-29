package com.market.wingy.auth.controller;

import com.market.wingy.auth.service.ShopMemberAuthenticationService;
import com.market.wingy.security.model.AuthenticationRequest;
import com.market.wingy.security.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/shop-member")
@RequiredArgsConstructor
public class ShopMemberAuthenticationController {

    private final ShopMemberAuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}