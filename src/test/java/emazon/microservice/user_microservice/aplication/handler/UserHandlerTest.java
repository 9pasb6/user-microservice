//package emazon.microservice.user_microservice.aplication.handler;
//
//import emazon.microservice.user_microservice.aplication.handler.impl.UserHandler;
//import emazon.microservice.user_microservice.aplication.mapper.request.UserRequestMapper;
//import emazon.microservice.user_microservice.domain.model.User;
//import emazon.microservice.user_microservice.domain.usecase.UserUseCase;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.HashSet;
//
//import static org.mockito.Mockito.*;
//
//public class UserHandlerTest {
//
//    @Mock
//    private UserUseCase userUseCase;
//
//    @Mock
//    private UserRequestMapper userMapper;
//
//    @InjectMocks
//    private UserHandler userHandler;
//
//    public UserHandlerTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateUser() {
//        CreateUserDTO createUserDTO = new CreateUserDTO("Pedro Bernal", "pedro@example.com", "password", new HashSet<>());
//        User user = new User(null, "Pedro Bernal", "pedro@example.com", "password", new HashSet<>());
//
//        when(userMapper.toDomain(createUserDTO)).thenReturn(user);
//
//        userHandler.createUser(createUserDTO);
//
//        // Verifica que el método createUser en userUseCase haya sido llamado una vez con el usuario correcto
//        verify(userUseCase, times(1)).createUser(user);
//    }
//}