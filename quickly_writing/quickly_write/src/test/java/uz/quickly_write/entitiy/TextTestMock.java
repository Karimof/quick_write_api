package uz.quickly_write.entitiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextTestMock {

    @Mock
    Text text;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        text = new Text();
    }

    @Test
    void getText() {
        String data = "Salom";
        assertEquals(data,"Salom");
    }
}