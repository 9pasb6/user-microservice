package emazon.microservice.user_microservice.aplication.handler.impl;

import emazon.microservice.user_microservice.aplication.dto.request.RoleRequest;
import emazon.microservice.user_microservice.aplication.dto.response.RoleResponse;
import emazon.microservice.user_microservice.aplication.handler.IRolHandler;
import emazon.microservice.user_microservice.aplication.mapper.request.RoleRequestMapper;
import emazon.microservice.user_microservice.aplication.mapper.response.RoleResponseMapper;
import emazon.microservice.user_microservice.domain.api.IRoleServicePort;
import emazon.microservice.user_microservice.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleHandler implements IRolHandler {
    private final IRoleServicePort roleServicePort;
    private final RoleRequestMapper roleRequestMapper;
    private final RoleResponseMapper roleResponseMapper;

    @Override
    public void save(RoleRequest roleRequest) {
        Role role = roleRequestMapper.requestToRole(roleRequest);
        roleServicePort.save(role);
    }

    @Override
    public RoleResponse findById(Long id) {
        Role role = roleServicePort.findById(id);
        return roleResponseMapper.roleToResponse(role);
    }

    @Override
    public List<RoleResponse> findAll() {
        List<Role> roles = roleServicePort.findAll();
        return roles.stream()
                .map(roleResponseMapper::roleToResponse)
                .collect(Collectors.toList());
    }
}