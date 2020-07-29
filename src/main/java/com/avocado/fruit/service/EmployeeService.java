package com.avocado.fruit.service;

import com.avocado.fruit.dto.employee.EmployeeRequest;
import com.avocado.fruit.dto.employee.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse updateEmployeeByID(Long employeeId, EmployeeRequest employeeRequest);

    void removeEmployeeByID(Long employeeId);

    EmployeeResponse getEmployeeByID(Long employeeId);

    List<EmployeeResponse> getEmployees();

    EmployeeResponse getEmployeeByEmail(String email);
}
