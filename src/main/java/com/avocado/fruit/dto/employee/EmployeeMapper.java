package com.avocado.fruit.dto.employee;

import com.avocado.fruit.model.Employee;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EmployeeMapper {

    @Mapping(target = "roles", ignore = true)
    Employee mapToEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse mapToEmployeeResponse(Employee employee);

    @IterableMapping(elementTargetType = Employee.class)
    List<Employee> mapToEmployees(List<EmployeeRequest> employeeRequests);

    @IterableMapping(elementTargetType = EmployeeResponse.class)
    List<EmployeeResponse> mapToEmployeeResponses(List<Employee> employees);
}
