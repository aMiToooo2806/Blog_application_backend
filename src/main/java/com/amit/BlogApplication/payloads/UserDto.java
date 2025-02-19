package com.amit.BlogApplication.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 2,message = "Username must be min of 2 characters..!")
    private String name;

    @NotEmpty
    @Email(message = "Email address is not valid..!")
    private String email;

    @NotEmpty
    @Size(min = 5,max = 11,message = "Password should be more than 5 characters and less than 11 characters")
    private String password;

    @NotEmpty

    private String about;

    public UserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
