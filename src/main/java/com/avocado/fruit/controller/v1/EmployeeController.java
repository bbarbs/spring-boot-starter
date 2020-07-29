package com.avocado.fruit.controller.v1;

import com.avocado.fruit.dto.employee.EmployeeRequest;
import com.avocado.fruit.dto.employee.EmployeeResponse;
import com.avocado.fruit.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/v1/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(
            value = "/employees",
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<EmployeeResponse> getEmployees() {
        return employeeService.getEmployees();
    }

    @PostMapping(
            value = "/employees",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.createEmployee(employeeRequest);
    }

    @GetMapping(
            value = "/employees/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasAuthority('READ')"
    )
    @ResponseBody
    public EmployeeResponse getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeByID((long) id);
    }

    @DeleteMapping(
            value = "/employees/{id}",
            produces = TEXT_PLAIN_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        employeeService.removeEmployeeByID((long) id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(
            value = "/employees/{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    @ResponseBody
    public EmployeeResponse updateEmployeeById(@PathVariable int id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployeeByID((long) id, employeeRequest);
    }

    @GetMapping(
            value = "/employees/current",
            produces = APPLICATION_JSON_VALUE
    )
    public User getCurrentLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}