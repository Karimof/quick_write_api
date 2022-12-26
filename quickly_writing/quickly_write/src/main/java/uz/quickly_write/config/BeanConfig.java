package uz.quickly_write.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uz.quickly_write.repository.GroupRepo;
import uz.quickly_write.repository.TextRepo;
import uz.quickly_write.repository.UserRepo;
import uz.quickly_write.service.UserService;


@Configuration
public class BeanConfig {

    @Bean
    @Scope("prototype")
    public UserService userService(UserRepo userRepo, GroupRepo groupRepo, TextRepo textRepo) {
        return new UserService(userRepo, groupRepo, textRepo);
    }
}
