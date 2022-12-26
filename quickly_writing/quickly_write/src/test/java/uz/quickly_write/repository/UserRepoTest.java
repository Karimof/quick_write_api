package uz.quickly_write.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uz.quickly_write.entitiy.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class) // for MockMvc test -> "MockitoExtension.class"
@SpringBootTest
class UserRepoTest {

    @Autowired
    UserRepo userRepo; // Shu classni o'zi @Autowired bo'ladi yoki yo'q
    // lekin barcha qolganlari "@Mock" bo'ladi aniq.

    //* MockMvc da esa shu classni o'zi "@InjectMocks" bo'ladi


    @Test
    void existsUserByUserName() {
        boolean b = userRepo.existsUserByUserName("fff");
        assertTrue(b);
    }

    @Test
    void findByUserName() {
        Optional<User> user = userRepo.findByUserName("fff");
        assertEquals("fff@gmail.com", user.get().getEmail());
    }

    @Test
    void findAllByGroup_Name() {
        //TODO here
    }
}