package uz.quickly_write.service;

import org.springframework.ui.Model;
import uz.quickly_write.entitiy.User;
import uz.quickly_write.model.UserDto;
import uz.quickly_write.repository.GroupRepo;
import uz.quickly_write.repository.TextRepo;
import uz.quickly_write.repository.UserRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public class UserService {
    final
    UserRepo userRepo;
    final
    GroupRepo groupRepo;
    final
    TextRepo textRepo;

    public String name;

    public UserService(UserRepo userRepo, GroupRepo groupRepo, TextRepo textRepo) {
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
        this.textRepo = textRepo;
    }


    public boolean addUser(UserDto newUser, Model model) {
        String message = "";
        if (userRepo.existsUserByUserName(newUser.getUserName())) {
            message = "Bunday foydalanuvchi mavjud!";
            model.addAttribute("message", message);
            return false;
        }
        if (newUser.getPassword().equals(newUser.getCheckPassword())) {
            User user = new User(newUser.getFullName(), newUser.getUserName(), newUser.getEmail(), newUser.getPassword());
            userRepo.save(user);
            message = "Muvaffaqqiyatli ro'yhatdan o'tdingiz.";
            model.addAttribute("message", message);
            return true;
        }
        message = "Parolni to'g'ri takrorlang";
        model.addAttribute("message", message);
        return false;
    }

    public boolean loginService(String userName, String password, Model model, HttpServletRequest request) {
        Optional<User> optionalUser = userRepo.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            return false;
        }
        user.setActive(true);
        User savedUser = userRepo.save(user);

        request.getSession().setAttribute("userName", savedUser.getUserName());
        model.addAttribute("message", "Tizimga kirdingiz");
        this.name = savedUser.getUserName();
        return true;
    }
}
