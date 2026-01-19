package com.amit.BlogApplication.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
}
