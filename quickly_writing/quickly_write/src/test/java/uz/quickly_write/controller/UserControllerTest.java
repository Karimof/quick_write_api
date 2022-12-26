package uz.quickly_write.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserControllerTest {

    @Mock
    Model model;
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController();
    }

    @Test
    void index2() {
        String indexName = userController.index2(model);
        assertEquals("index", indexName);
        verify(model, times(1)).addAttribute(eq("forTest"), anySet());
    }
}