package emazon.microservice.user_microservice.usecase;


import emazon.microservice.user_microservice.domain.exception.RoleExceptions;
import emazon.microservice.user_microservice.domain.exception.UserExceptions;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.domain.spi.IRolePersistencePort;
import emazon.microservice.user_microservice.domain.spi.IUserPersistencePort;
import emazon.microservice.user_microservice.domain.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class UserUseCaseTest {

    @InjectMocks
    private UserUseCase userUseCase;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ValidUserWithDefaultRole_Success() {
        User user = new User();
        user.setName("John");
        user.setLastName("Doe");
        user.setIdentityDocument("123456");
        user.setPhoneNumber("+123456789");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        Role defaultRole = new Role();
        defaultRole.setName("CUSTOMER");

        when(userPersistencePort.findByIdentityDocument(user.getIdentityDocument())).thenReturn(null);
        when(rolePersistencePort.findByName("CUSTOMER")).thenReturn(defaultRole);

        userUseCase.createUser(user);

        verify(userPersistencePort).createUser(user);
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(defaultRole));
    }

    @Test
    void createUser_UserAlreadyExists_ThrowsException() {
        User user = new User();
        user.setIdentityDocument("123456");
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        when(userPersistencePort.findByIdentityDocument(user.getIdentityDocument())).thenReturn(new User());

        assertThrows(UserExceptions.UserAlreadyExistsException.class, () -> {
            userUseCase.createUser(user);
        });
    }

    @Test
     void createUser_NoDefaultRoleFound_ThrowsException() {
        User user = new User();
        user.setRoles(null);

        when(userPersistencePort.findByIdentityDocument(user.getIdentityDocument())).thenReturn(null);
        when(rolePersistencePort.findByName("CUSTOMER")).thenReturn(null);

        assertThrows(RoleExceptions.RoleNotFoundException.class, () -> {
            userUseCase.createUser(user);
        });
    }

    @Test
     void createUser_ValidUserWithCustomRoles_Success() {
        User user = new User();
        user.setName("John");
        user.setLastName("Doe");
        user.setIdentityDocument("123456");
        user.setPhoneNumber("+123456789");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        Role adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName("ADMIN");

        Role userRole = new Role();
        userRole.setId(2L);
        userRole.setName("USER");

        user.setRoles(List.of(adminRole, userRole));

        when(userPersistencePort.findByIdentityDocument(user.getIdentityDocument())).thenReturn(null);
        when(rolePersistencePort.getRolesByIds(Set.of(1L, 2L))).thenReturn(List.of(adminRole, userRole));

        userUseCase.createUser(user);

        verify(userPersistencePort).createUser(user);
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(adminRole));
        assertTrue(user.getRoles().contains(userRole));
    }

    @Test
     void createUser_CustomRolesNotFound_ThrowsException() {
        User user = new User();
        Role adminRole = new Role();
        adminRole.setId(1L);
        user.setRoles(List.of(adminRole));

        when(userPersistencePort.findByIdentityDocument(user.getIdentityDocument())).thenReturn(null);
        when(rolePersistencePort.getRolesByIds(Set.of(1L))).thenReturn(List.of());

        assertThrows(RoleExceptions.RoleNotFoundException.class, () -> {
            userUseCase.createUser(user);
        });
    }

    @Test
     void getAllUsers_Success() {
        List<User> users = List.of(new User(), new User());

        when(userPersistencePort.getAllUsers()).thenReturn(users);

        List<User> result = userUseCase.getAllUsers();

        assertEquals(users, result);
    }

    @Test
     void getUserById_UserExists_ReturnsUser() {
        User user = new User();
        Long id = 1L;

        when(userPersistencePort.getUserById(id)).thenReturn(user);

        User result = userUseCase.getUserById(id);

        assertEquals(user, result);
    }

    @Test
     void getUserById_UserNotFound_ThrowsException() {
        Long id = 1L;

        when(userPersistencePort.getUserById(id)).thenReturn(null);

        assertThrows(UserExceptions.UserNotFoundException.class, () -> {
            userUseCase.getUserById(id);
        });
    }

    @Test
     void assignRoleToUser_Success() {
        User user = new User();
        Long id = 1L;
        String roleName = "ADMIN";
        Role role = new Role();
        role.setName(roleName);

        user.setRoles(new ArrayList<>());

        when(userPersistencePort.getUserById(id)).thenReturn(user);
        when(rolePersistencePort.findByName(roleName)).thenReturn(role);

        userUseCase.assignRoleToUser(id, roleName);

        verify(userPersistencePort).createUser(user);
        assertTrue(user.getRoles().contains(role));
    }



    @Test
     void assignRoleToUser_RoleNotFound_ThrowsException() {
        User user = new User();
        Long id = 1L;
        String roleName = "ADMIN";

        when(userPersistencePort.getUserById(id)).thenReturn(user);
        when(rolePersistencePort.findByName(roleName)).thenReturn(null);

        assertThrows(RoleExceptions.RoleNotFoundException.class, () -> {
            userUseCase.assignRoleToUser(id, roleName);
        });
    }

    @Test
     void removeRoleFromUser_Success() {
        Role role = new Role();
        role.setId(1L);
        role.setName("CUSTOMER");

        Long id = 1L;

        User user = new User();
        user.setId(id);
        user.setName("John");
        user.setLastName("Doe");
        user.setIdentityDocument("123456");
        user.setPhoneNumber("+123456789");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        user.setRoles(new ArrayList<>(List.of(role)));

        String roleName = "CUSTOMER";

        when(userPersistencePort.getUserById(id)).thenReturn(user);
        when(rolePersistencePort.findByName(roleName)).thenReturn(role);

        userUseCase.removeRoleFromUser(id, roleName);

        verify(userPersistencePort).createUser(user);
        assertFalse(user.getRoles().contains(role));
    }

    @Test
     void removeRoleFromUser_UserNotFound_ThrowsException() {
        Long id = 1L;
        String roleName = "ADMIN";

        when(userPersistencePort.getUserById(id)).thenReturn(null);

        assertThrows(UserExceptions.UserNotFoundException.class, () -> {
            userUseCase.removeRoleFromUser(id, roleName);
        });
    }

    @Test
     void removeRoleFromUser_RoleNotFound_ThrowsException() {
        User user = new User();
        Long id = 1L;
        String roleName = "ADMIN";

        when(userPersistencePort.getUserById(id)).thenReturn(user);
        when(rolePersistencePort.findByName(roleName)).thenReturn(null);

        assertThrows(RoleExceptions.RoleNotFoundException.class, () -> {
            userUseCase.removeRoleFromUser(id, roleName);
        });
    }

    @Test
     void removeRoleFromUser_RoleNotAssigned_ThrowsException() {
        Role role = new Role();
        role.setId(1L);
        role.setName("CUSTOMER");

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setRoles(new ArrayList<>());

        Long id = 1L;
        String roleName = "CUSTOMER";
        Role roleRemove = new Role();
        roleRemove.setName(roleName);

        when(userPersistencePort.getUserById(id)).thenReturn(user);
        when(rolePersistencePort.findByName(roleName)).thenReturn(roleRemove);

        assertThrows(RoleExceptions.RoleNotAssignedToUserException.class, () -> {
            userUseCase.removeRoleFromUser(id, roleName);
        });
    }
}