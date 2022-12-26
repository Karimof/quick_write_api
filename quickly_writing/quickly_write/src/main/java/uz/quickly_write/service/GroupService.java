package uz.quickly_write.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.quickly_write.entitiy.Group;
import uz.quickly_write.entitiy.User;
import uz.quickly_write.repository.GroupRepo;
import uz.quickly_write.repository.UserRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Service
public class GroupService {
    final
    GroupRepo repoGroup;

    final
    UserRepo repoUser;

    @Autowired
    public GroupService(GroupRepo repoGroup, UserRepo repoUser) {
        this.repoGroup = repoGroup;
        this.repoUser = repoUser;
    }


    public boolean addToGroup(Group group, HttpServletRequest request) {

        if (!group.getName().equals("")) {
            group.setName(group.getName().toLowerCase());

            Optional<Group> optionalGroup = repoGroup.findByName(group.getName());

            if (optionalGroup.isPresent()) {
                Group findByNameGroup = optionalGroup.get();
                if (findByNameGroup.getPassword().equals(group.getPassword())) {
                    Optional<User> optionalUser = repoUser.findByUserName(request.getSession().getAttribute("userName").toString());
                    User user = optionalUser.get();
                    user.setGroup(findByNameGroup);
                    repoUser.save(user);
                    return true;
                }
            }
            if (!repoGroup.existsByName(group.getName()) && request.getSession().getAttribute("user") != null) {
                Group savedGroup = repoGroup.save(group);
                User user = (User) request.getSession().getAttribute("user");
                user.setGroup(savedGroup);
                User savedUser = repoUser.save(user);
                return true;
            }
        }
        return false;
    }
}
