package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.UserDto;
import com.amit.BlogApplication.services.Impl.UserServiceImpl;
import com.amit.BlogApplication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto>createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<?>updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId)
    {
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<?>deleteUser(@PathVariable Integer userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(Map.of("Message","User deleted successfully"),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>>getAllUsers()
    {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("ById/{userId}")
    private ResponseEntity<UserDto>getUserById(@PathVariable Integer userId)
    {
        UserDto userById = this.userService.getUserById(userId);
        return ResponseEntity.ok(userById);
    }
}
