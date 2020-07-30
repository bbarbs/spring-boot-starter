package com.avocado.fruit.service.impl;


import com.avocado.fruit.exception.EmployeeNotFoundException;
import com.avocado.fruit.exception.RoleNotFoundException;
import com.avocado.fruit.exception.config.ErrorCodes;
import com.avocado.fruit.model.Employee;
import com.avocado.fruit.model.Role;
import com.avocado.fruit.repository.EmployeeRepository;
import com.avocado.fruit.repository.RoleRepository;
import com.avocado.fruit.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public EmployeeRoleServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRoleToEmployeeByID(Long employeeId, Long roleId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found", ErrorCodes.EMPLOYEE_ID_NOT_FOUND));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found", ErrorCodes.ROLE_ID_NOT_FOUND));
        employee.getRoles().add(role);
    }
}
