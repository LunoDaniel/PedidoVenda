package com.pedidovenda.controller;

import com.pedidovenda.security.AuthService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
@RequestScoped
public class LoginController {
    
    private String email;
    private String senha;
    
    @Inject
    private AuthService authService;
    
    @Inject
    private FacesContext facesContext;
    
    public String login() {
        try {
            HttpServletRequest request = (HttpServletRequest) 
                facesContext.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse)
                facesContext.getExternalContext().getResponse();
            
            authService.login(email, senha, request, response);
            
            return "/pedidos?faces-redirect=true";
        } catch (AuthenticationException e) {
            facesContext.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Login falhou", "Email ou senha inválidos"));
            return null;
        }
    }
}