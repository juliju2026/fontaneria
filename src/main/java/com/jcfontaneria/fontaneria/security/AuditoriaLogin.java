package com.jcfontaneria.fontaneria.security;

import com.jcfontaneria.fontaneria.entity.LogAcceso;
import com.jcfontaneria.fontaneria.repository.LogAccesoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuditoriaLogin implements AuthenticationSuccessHandler,
        AuthenticationFailureHandler {

    private final LogAccesoRepository logRepository;

    public AuditoriaLogin(LogAccesoRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        guardar(authentication.getName(), "CORRECTO", request);
        response.sendRedirect(request.getContextPath() + "/inicio");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        guardar(username, "FALLIDO", request);
        response.sendRedirect(request.getContextPath() + "/login?error");
    }

    private void guardar(String username, String resultado,
                         HttpServletRequest request) {
        LogAcceso log = new LogAcceso();
        log.setUsername(username == null ? "desconocido" : username);
        log.setResultado(resultado);
        log.setDireccionIp(request.getRemoteAddr());
        logRepository.save(log);
    }
}
