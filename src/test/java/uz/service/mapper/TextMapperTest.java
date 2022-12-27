package uz.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextMapperTest {

    private TextMapper textMapper;

    @BeforeEach
    public void setUp() {
        textMapper = new TextMapperImpl();
    }
}
