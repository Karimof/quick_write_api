package uz.quickly_write.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uz.quickly_write.entitiy.Group;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This testing with JUnit5
@ExtendWith(SpringExtension.class)
@SpringBootTest
class GroupRepoTest {
    @Autowired
    GroupRepo groupRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    void existsByName() {
    }

    @Test
    void findByName() {
        Optional<Group> optionalGroup = groupRepo.findByName("g1");
        assertEquals("g1", optionalGroup.get().getName());
    }
}