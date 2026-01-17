package com.amit.BlogApplication.config;

import com.amit.BlogApplication.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    @Autowired
    private UserRepo userRepo;

    public boolean isOwner(Integer userId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName(); // Spring Security username

        return userRepo.findById(userId)
                .map(user -> user.getUsername().equals(loggedInUsername))
                .orElse(false);
    }
}
