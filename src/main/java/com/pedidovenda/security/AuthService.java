package com.pedidovenda.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ApplicationScoped
public class AuthService {

    @Inject
    private SecurityContext securityContext;

    public void login(String username,
                      String password,
                      HttpServletRequest request,
                      HttpServletResponse response) throws AuthenticationException {

        UsernamePasswordCredential credential =
                new UsernamePasswordCredential(username, password);

        var status = securityContext.authenticate(
                request,
                response,
                AuthenticationParameters.withParams()
                        .credential(credential)
        );

        switch (status) {
            case SUCCESS:
                return;

            case SEND_FAILURE:
            case SEND_CONTINUE:
                throw new AuthenticationException("Falha na autenticação");

            default:
                throw new AuthenticationException("Status de autenticação desconhecido");
        }
    }
}
