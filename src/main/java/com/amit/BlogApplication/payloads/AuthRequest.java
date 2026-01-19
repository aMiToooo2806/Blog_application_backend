package com.amit.BlogApplication.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthRequest {
    @Schema(example = "Amit")
    private String username;

    @Schema(example = "amit@123")
    private String password;
}
