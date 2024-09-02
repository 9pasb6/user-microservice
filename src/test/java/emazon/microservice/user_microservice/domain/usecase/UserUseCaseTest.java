//package emazon.microservice.user_microservice.domain.usecase;
//
//import emazon.microservice.user_microservice.domain.model.User;
//
//import emazon.microservice.user_microservice.domain.spi.IUserPersistencePort;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.HashSet;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.times;
//
//public class UserUseCaseTest {
//
//    @Mock
//    private IUserPersistencePort userPersistencePort;
//
//    @InjectMocks
//    private UserUseCase userUseCase;
//
//    public UserUseCaseTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateUser() {
//        User user = new User(null, "Pedro Bernal", "pedro@example.com", "password", new HashSet<>());
//
//        userUseCase.createUser(user);
//
//        // Verifica que el método createUser en userPersistencePort haya sido llamado una vez con el usuario correcto
//        verify(userPersistencePort, times(1)).createUser(user);
//    }
//}