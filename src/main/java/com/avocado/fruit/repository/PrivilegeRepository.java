package com.avocado.fruit.repository;

import com.avocado.fruit.model.Privilege;
import com.avocado.fruit.model.enums.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByName(Privileges name);
}
