package com.pedidovenda.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;
import java.util.Set;

@AllArgsConstructor
public class AppUserDetails implements Principal {

    private final String email;
    @Getter
    private final Set<String> roles;

    @Override
    public String getName() {
        return email;
    }

    public boolean isInRole(String role) {
        return roles.contains(role);
    }
}