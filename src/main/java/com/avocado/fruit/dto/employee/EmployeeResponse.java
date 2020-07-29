package com.avocado.fruit.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
}
