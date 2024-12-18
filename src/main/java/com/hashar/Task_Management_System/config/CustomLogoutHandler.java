package com.hashar.Task_Management_System.config;

import com.hashar.Task_Management_System.model.Token;
import com.hashar.Task_Management_System.repo.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    TokenRepo tokenRepo;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);  // trim "bearer "
            Token storedToken = tokenRepo.findByAccessToken(token).orElse(null);
            if (storedToken != null){
                storedToken.setLoggedOut(true);
                tokenRepo.save(storedToken);
            }
        }

    }
}
