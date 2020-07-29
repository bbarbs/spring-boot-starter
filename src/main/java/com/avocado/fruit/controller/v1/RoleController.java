package com.avocado.fruit.controller.v1;

import com.avocado.fruit.dto.role.RoleResponse;
import com.avocado.fruit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/api")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(
            value = "/roles",
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<RoleResponse> getRoles() {
        return roleService.getRoles();
    }
}
