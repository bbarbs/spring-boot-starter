package com.avocado.fruit.dto.role;

import com.avocado.fruit.model.enums.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {

    private Long id;
    private Roles name;
}
