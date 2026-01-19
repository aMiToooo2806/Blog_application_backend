package com.amit.BlogApplication.controllers;

import com.amit.BlogApplication.payloads.UserDto;
import com.amit.BlogApplication.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "User APIs", description = "Operations related to users")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create/Register user",
            description = "Creates a new user with default ROLE_USER. (ADMIN/USER allowed in your security rules)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Update user",
            description = "Update user details. Admin can update any user. User can update only self.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') OR @userSecurity.isOwner(#userId)")
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @Valid @RequestBody UserDto userDto,
            @Parameter(description = "User ID to update", example = "1")
            @PathVariable Integer userId) {

        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user", description = "Delete user by id. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "User ID to delete", example = "1")
            @PathVariable Integer userId) {

        this.userService.deleteUser(userId);
        return new ResponseEntity<>(Map.of("Message", "User deleted successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get all users", description = "Fetch all users. Admin only.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users list returned"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @Operation(summary = "Get user by id",
            description = "Admin can access any user. User can access only self.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN') OR @userSecurity.isOwner(#userId)")
    @GetMapping("/ById/{userId}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Integer userId) {

        UserDto userById = this.userService.getUserById(userId);
        return ResponseEntity.ok(userById);
    }
}
