package uz.quickly_write.controller;

import org.apache.jasper.runtime.PageContextImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.quickly_write.entitiy.Group;
import uz.quickly_write.entitiy.User;
import uz.quickly_write.repository.UserRepo;
import uz.quickly_write.service.GroupService;
import uz.quickly_write.service.SinovService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SinovController {
    @Autowired
    SinovService sinovService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserRepo repoUser;



    @GetMapping("/sinov")
    public String sinov(Model model) {
        model.addAttribute("text", sinovService.findByRandomId().getText());
        return "sinov";
    }

    @GetMapping("/guruh")
    public String guruhUrl(HttpServletRequest request, Model model) {
        model.addAttribute("userName",request.getSession().getAttribute("userName").toString());
        return "guruh";
    }

    @GetMapping("/guruhWindow")
    public String guruh(Group group, HttpServletRequest request, Model model, PageContextImpl pageContext) {
        boolean b = groupService.addToGroup(group, request);
        if (b) {
            model.addAttribute("gName", group.getName());
            model.addAttribute("gPass", group.getPassword());

            List<String> userNames = new ArrayList<>();
            List<User> allUserByGroupName = repoUser.findAllByGroup_Name(group.getName());
            for (User user : allUserByGroupName) {
                userNames.add(user.getUserName());
            }
            List<Integer> inn = new ArrayList<>();
            inn.add(1);
            inn.add(2);
            inn.add(3);
            model.addAttribute("userNames", inn);
            model.addAttribute("userName", request.getSession().getAttribute("userName").toString());
            pageContext.setAttribute("userNames2",inn);
            return "guruhmusoboqasi";
        } else {
            model.addAttribute("message", "Bunday nomli guruh mavjud. Yoki parol hato!");
            return "guruh";
        }
    }
}
