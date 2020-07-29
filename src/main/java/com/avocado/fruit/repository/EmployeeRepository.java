package com.avocado.fruit.repository;

import com.avocado.fruit.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Modifying
    @Query("UPDATE Employee emp SET emp.email = :email, emp.firstName = :firstName, emp.lastName = :lastName WHERE emp.id = :empId")
    void updateEmployee(@Param("empId") Long id, @Param("email")String email, @Param("firstName")String firstName, @Param("lastName")String lastName);
}
