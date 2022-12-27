package uz.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import uz.domain.Role;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RoleRepositoryWithBagRelationshipsImpl implements RoleRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> fetchBagRelationships(Optional<Role> role) {
        return role.map(this::fetchCustomers);
    }

    @Override
    public Page<Role> fetchBagRelationships(Page<Role> roles) {
        return new PageImpl<>(fetchBagRelationships(roles.getContent()), roles.getPageable(), roles.getTotalElements());
    }

    @Override
    public List<Role> fetchBagRelationships(List<Role> roles) {
        return Optional.of(roles).map(this::fetchCustomers).orElse(Collections.emptyList());
    }

    Role fetchCustomers(Role result) {
        return entityManager
            .createQuery("select role from Role role left join fetch role.customers where role is :role", Role.class)
            .setParameter("role", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Role> fetchCustomers(List<Role> roles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, roles.size()).forEach(index -> order.put(roles.get(index).getId(), index));
        List<Role> result = entityManager
            .createQuery("select distinct role from Role role left join fetch role.customers where role in :roles", Role.class)
            .setParameter("roles", roles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
