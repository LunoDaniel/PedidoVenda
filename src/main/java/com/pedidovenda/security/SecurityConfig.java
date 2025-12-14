package com.pedidovenda.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@FormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage = "/login.xhtml",
        errorPage = "/login.xhtml?error=true",
        useForwardToLogin = false
    )
)
@DatabaseIdentityStoreDefinition(
    dataSourceLookup = "java:comp/env/jdbc/PedidoVendaDS",
    callerQuery = "SELECT senha FROM usuario WHERE email = ?",
    groupsQuery = "SELECT u.grupo FROM usuario u WHERE u.email = ?",
    hashAlgorithm = Pbkdf2PasswordHash.class,
    hashAlgorithmParameters = {
        "Pbkdf2PasswordHash.Iterations=3072",
        "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
        "Pbkdf2PasswordHash.SaltSizeBytes=64"
    }
)
@ApplicationScoped
public class SecurityConfig {
}