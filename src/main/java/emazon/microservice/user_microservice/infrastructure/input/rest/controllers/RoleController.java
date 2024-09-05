//package emazon.microservice.user_microservice.infrastructure.controllers;
//
//import emazon.microservice.user_microservice.aplication.dto.request.RoleRequest;
//import emazon.microservice.user_microservice.aplication.handler.impl.RoleHandler;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/roles")
//public class RoleController {
//    private final RoleHandler roleHandler;
//
//    public RoleController(RoleHandler roleHandler) {
//        this.roleHandler = roleHandler;
//    }
//
//    @PostMapping
//    public ResponseEntity<RoleRequest> createRole(@RequestBody RoleRequest roleDTO) {
//        RoleRequest createdRole = roleHandler.createRole(roleDTO);
//        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<RoleRequest>> getAllRoles() {
//        List<RoleRequest> roles = roleHandler.getAllRoles();
//        return new ResponseEntity<>(roles, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<RoleRequest> getRoleById(@PathVariable Long id) {
//        RoleRequest role = roleHandler.getRoleById(id);
//        return new ResponseEntity<>(role, HttpStatus.OK);
//    }
//
//
//}