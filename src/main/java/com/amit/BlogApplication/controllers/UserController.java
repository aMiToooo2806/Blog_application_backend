package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.UserDto;
import com.amit.BlogApplication.services.Impl.UserServiceImpl;
import com.amit.BlogApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    private ResponseEntity<UserDto>createUser(@RequestBody UserDto userDto)
    {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
