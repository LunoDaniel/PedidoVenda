package com.pedidovenda.controller;

import com.pedidovenda.security.AuthService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Named
@ViewScoped
public class LoginController implements Serializable {
    
    private String email;
    private String senha;
    
    @Inject
    private AuthService authService;
    
    @Inject
    private FacesContext facesContext;

    public String login() {
        FacesContext fc = FacesContext.getCurrentInstance();

        try {
            HttpServletRequest request =
                    (HttpServletRequest) fc.getExternalContext().getRequest();
            HttpServletResponse response =
                    (HttpServletResponse) fc.getExternalContext().getResponse();

            authService.login(email, senha, request, response);

            return "/pedidos?faces-redirect=true";

        } catch (AuthenticationException e) {
            fc.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Login falhou", "Email ou senha inválidos"));
            return null;
        }
    }
}