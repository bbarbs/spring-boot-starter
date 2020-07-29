package com.avocado.fruit.dto.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class LoginRequest {

    @Email(message = "Email must be valid")
    private String email;
    private String password;
}
