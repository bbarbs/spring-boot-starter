package com.avocado.fruit.controller.v1;


import com.avocado.fruit.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/api")
public class EmployeeRoleController {

    private EmployeeRoleService employeeRoleService;

    @Autowired
    public EmployeeRoleController(EmployeeRoleService employeeRoleService) {
        this.employeeRoleService = employeeRoleService;
    }

    @PutMapping(
            value = "/employees/{employeeId}/roles/{roleId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addRoleToEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                               @PathVariable(name = "roleId") Long roleId) {
        employeeRoleService.addRoleToEmployeeByID(employeeId, roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
