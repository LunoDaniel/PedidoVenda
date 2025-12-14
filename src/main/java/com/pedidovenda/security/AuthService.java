package com.pedidovenda.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;

@ApplicationScoped
public class AuthService implements Serializable {

    @Inject
    private IdentityStoreHandler identityStoreHandler;
    
    public CredentialValidationResult.Status login(
        String username, 
        String password,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {
        
        UsernamePasswordCredential credential =
            new UsernamePasswordCredential(username, password);

        CredentialValidationResult credentialValidationResult = identityStoreHandler.validate(
                credential
        );
        return credentialValidationResult.getStatus();
    }
}