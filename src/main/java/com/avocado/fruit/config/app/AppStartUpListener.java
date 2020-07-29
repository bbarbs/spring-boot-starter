package com.avocado.fruit.config.app;

import com.avocado.fruit.model.Privilege;
import com.avocado.fruit.model.Role;
import com.avocado.fruit.model.enums.Privileges;
import com.avocado.fruit.model.enums.Roles;
import com.avocado.fruit.repository.PrivilegeRepository;
import com.avocado.fruit.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class AppStartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    boolean isAlreadySetup = false;

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public AppStartUpListener(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Transactional
    Optional<Privilege> createPrivilegeIfNotFound(Privileges name) {
        Optional<Privilege> optional = privilegeRepository.findByName(name);
        if (optional.isEmpty()) {
            Privilege privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
            return Optional.of(privilege);
        }
        return optional;
    }

    @Transactional
    void createRoleIfNotFound(Roles name, Set<Privilege> privileges) {
        Optional<Role> optional = roleRepository.findByName(name);
        if (optional.isEmpty()) {
            Role role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isAlreadySetup)
            return;

        Optional<Privilege> readOptional = createPrivilegeIfNotFound(Privileges.READ);
        Optional<Privilege> writeOptional = createPrivilegeIfNotFound(Privileges.WRITE);

        if(readOptional.isPresent() && writeOptional.isPresent()) {
            Privilege readPrivilege = readOptional.get();
            Privilege writePrivilege = writeOptional.get();

            Set<Privilege> adminPrivileges = new HashSet<>();
            adminPrivileges.add(readPrivilege);
            adminPrivileges.add(writePrivilege);

            Set<Privilege> applicantPrivileges = new HashSet<>();
            applicantPrivileges.add(readPrivilege);

            createRoleIfNotFound(Roles.ROLE_ADMIN, adminPrivileges);
            createRoleIfNotFound(Roles.ROLE_APPLICANT, applicantPrivileges);
        }

        isAlreadySetup = true;
    }
}
