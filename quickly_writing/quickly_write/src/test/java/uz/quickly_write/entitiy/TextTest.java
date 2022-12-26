package uz.quickly_write.entitiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextTest {
    Text text;

    @BeforeEach
    void setUp() {
        this.text = new Text();
    }

    @Test
    void getText() {
        text.setText("Salom");
        assertEquals("Salom", text.getText());
    }
}