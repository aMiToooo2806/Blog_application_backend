package com.amit.BlogApplication.config;

import com.amit.BlogApplication.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("postSecurity")
public class PostSecurity {

    @Autowired
    private PostRepo postRepo;

    public boolean isPostOwner(Integer postId) {
        String loggedInUsername = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return postRepo.existsByPostIdAndUsers_Username(postId, loggedInUsername);
    }
}
