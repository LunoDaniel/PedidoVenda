package com.pedidovenda.security;

import java.security.Principal;
import java.util.Set;

public class AppUserDetails implements Principal {

    private final String email;
    private final Set<String> roles;



    public AppUserDetails(String email, Set<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    @Override
    public String getName() {
        return email;
    }
    
    public Set<String> getRoles() {
        return roles;
    }
    
    public boolean isInRole(String role) {
        return roles.contains(role);
    }
}