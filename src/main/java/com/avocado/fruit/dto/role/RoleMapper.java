package com.avocado.fruit.dto.role;

import com.avocado.fruit.model.Role;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {

    @Mapping(target = "privileges", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Role mapToRole(RoleRequest roleRequest);

    RoleResponse mapToRoleResponse(Role role);

    @IterableMapping(elementTargetType = Role.class)
    List<Role> mapToRoles(List<RoleRequest> roleRequests);

    @IterableMapping(elementTargetType = RoleResponse.class)
    List<RoleResponse> mapToRoleResponses(List<Role> roles);
}
