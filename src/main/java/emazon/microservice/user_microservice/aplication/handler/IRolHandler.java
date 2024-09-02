package emazon.microservice.user_microservice.aplication.handler;

import emazon.microservice.user_microservice.aplication.dto.request.RoleRequest;
import emazon.microservice.user_microservice.aplication.dto.response.RoleResponse;
import emazon.microservice.user_microservice.domain.model.Role;

import java.util.List;

public interface IRolHandler {

    void save(RoleRequest roleRequest);

    RoleResponse findById(Long id);

    List<RoleResponse> findAll();
}
