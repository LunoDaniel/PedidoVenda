package com.pedidovenda.util.mail;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

@Getter
@ApplicationScoped
public class EmailConfig {

    private String host;
    private int port;
    private String username;
    private String password;
    private boolean sslEnabled;
    private String from;

}