package com.avocado.fruit.service.impl;

import com.avocado.fruit.dto.role.RoleMapper;
import com.avocado.fruit.dto.role.RoleResponse;
import com.avocado.fruit.model.Role;
import com.avocado.fruit.repository.RoleRepository;
import com.avocado.fruit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponse> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.mapToRoleResponses(roles);
    }
}
