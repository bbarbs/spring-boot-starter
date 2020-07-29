package com.avocado.fruit.service.impl;

import com.avocado.fruit.dto.employee.EmployeeMapper;
import com.avocado.fruit.dto.employee.EmployeeRequest;
import com.avocado.fruit.dto.employee.EmployeeResponse;
import com.avocado.fruit.exception.*;
import com.avocado.fruit.model.Employee;
import com.avocado.fruit.model.Role;
import com.avocado.fruit.repository.EmployeeRepository;
import com.avocado.fruit.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements UserDetailsService, EmployeeService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder passwordEncoder, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Error validating email"));
        return new User(employee.getEmail(), employee.getPassword(), employee.isEnabled(),
                true, true, true, getAuthorities(employee.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
            role.getPrivileges()
                    .stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName().name()))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        boolean isEmailExist = employeeRepository.findByEmail(employeeRequest.getEmail()).isPresent();
        if (isEmailExist) {
            throw new EmailExistsException("Email " + employeeRequest.getEmail() + " already exists");
        }
        employeeRequest.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        Employee employee = employeeRepository.save(employeeMapper.mapToEmployee(employeeRequest));
        return employeeMapper.mapToEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployeeByID(Long employeeId, EmployeeRequest employeeRequest) {
        if (!employeeId.equals(employeeRequest.getId())) {
            throw new InconsistentIDException("IDs not match");
        }
        employeeRepository.updateEmployee(employeeId, employeeRequest.getEmail(), employeeRequest.getFirstName(), employeeRequest.getLastName());
        return getEmployeeByID(employeeId);
    }

    @Override
    public void removeEmployeeByID(Long employeeId) {
        EmployeeResponse employeeResponse = getEmployeeByID(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public EmployeeResponse getEmployeeByID(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found by ID: " + employeeId));
        return employeeMapper.mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.mapToEmployeeResponses(employees);
    }

    @Override
    public EmployeeResponse getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException("Employee not found by email: " + email));
        return employeeMapper.mapToEmployeeResponse(employee);
    }
}
