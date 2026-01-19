package com.amit.BlogApplication.payloads;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Schema(name = "UserDto", description = "User payload")
public class UserDto {

    @Schema(example = "1")
    private int id;

    @NotEmpty
    @Size(min = 2,message = "Username must be min of 2 characters..!")
    @Schema(example = "Amit")
    private String username;

    @NotEmpty
    @Email(message = "Email address is not valid..!")
    @Schema(example = "amit@gmail.com")
    private String email;

    @NotEmpty
    @Size(min = 5,max = 11,message = "Password should be more than 5 characters and less than 11 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, example = "amit@123")
    private String password;

    @NotEmpty
    @Schema(example = "I am Java Backend developer")
    private String about;

    @Schema(example = "[\"ROLE_USER\"]")
    private Set<String> roles = new HashSet<>();

    public UserDto() {
    }

}
