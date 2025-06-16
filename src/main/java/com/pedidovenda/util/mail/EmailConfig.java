package com.pedidovenda.util.mail;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class EmailConfig {

    @Inject
    @ConfigProperty(name = "email.host")
    private String host;

    @Inject
    @ConfigProperty(name = "email.port")
    private int port;

    @Inject
    @ConfigProperty(name = "email.username")
    private String username;

    @Inject
    @ConfigProperty(name = "email.password")
    private String password;

    @Inject
    @ConfigProperty(name = "email.ssl.enable", defaultValue = "true")
    private boolean sslEnabled;

    @Inject
    @ConfigProperty(name = "email.from", defaultValue = "noreply@pedidovenda.com")
    private String from;

    // Getters
    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isSslEnabled() { return sslEnabled; }
    public String getFrom() { return from; }
}