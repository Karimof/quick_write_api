package uz.quickly_write.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.quickly_write.entitiy.User;
import uz.quickly_write.model.UserDto;
import uz.quickly_write.repository.GroupRepo;
import uz.quickly_write.repository.TextRepo;
import uz.quickly_write.repository.UserRepo;
import uz.quickly_write.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepo repoUser;
    @Autowired
    GroupRepo repoGroup;
    @Autowired
    TextRepo repoText;


    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        String visiblity = "hidden";
        if (user != null) {
            model.addAttribute("userName", user.getUserName());
            visiblity = "visible";
        }
        model.addAttribute("visiblity", visiblity);

        return "index";
    }

    @PostMapping("/")
    public String index2(@Nullable Model model) {
//        model.addAttribute("forTest","str");
        return "index";
    }

    @GetMapping("/register_form")
    public String regForm(Model model) {
        model.addAttribute("message", "");
        return "register_form";
    }

    @PostMapping(value = "/register")
    public String add(UserDto newUser, Model model) {
        // TODO index ni o'rniga redirect ishlatish kerak.
        model.addAttribute("fullName", newUser.getFullName());
        return userService.addUser(newUser, model) ? "index" : "register_form";
    }

    @GetMapping(value = "/login_form")
    public String log_form(Model model) {
        model.addAttribute("message", "");
        return "login_form";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(name = "userName") String userName
            , @ModelAttribute(name = "password") String password,
                        Model model, HttpServletRequest request) {
        boolean passed = userService.loginService(userName, password, model, request);
        if (passed) {
            return "index";
        } else
            model.addAttribute("message", "Login yoki parol hato!");
        return "login_form";
    }
}
