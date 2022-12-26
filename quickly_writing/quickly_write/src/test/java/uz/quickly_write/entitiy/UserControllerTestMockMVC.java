package uz.quickly_write.entitiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uz.quickly_write.controller.UserController;
import uz.quickly_write.repository.UserRepo;
import uz.quickly_write.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class UserControllerTestMockMVC {

    @Mock
    UserService userService;
    @Mock
    UserRepo userRepo;

    UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();//TODO here...;
    }

    @Test
    void getId() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(text).build(); // The cause id "text"
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));
    }
}