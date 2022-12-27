package uz.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import uz.domain.Role;

public interface RoleRepositoryWithBagRelationships {
    Optional<Role> fetchBagRelationships(Optional<Role> role);

    List<Role> fetchBagRelationships(List<Role> roles);

    Page<Role> fetchBagRelationships(Page<Role> roles);
}
