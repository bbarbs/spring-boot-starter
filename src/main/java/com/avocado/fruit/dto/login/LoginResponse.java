package com.avocado.fruit.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginResponse implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 3157186499378371835L;

    private String token;
}
