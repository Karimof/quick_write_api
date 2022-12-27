package uz.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import uz.domain.*; // for static metamodels
import uz.domain.Group;
import uz.repository.GroupRepository;
import uz.service.criteria.GroupCriteria;
import uz.service.dto.GroupDTO;
import uz.service.mapper.GroupMapper;

/**
 * Service for executing complex queries for {@link Group} entities in the database.
 * The main input is a {@link GroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupDTO} or a {@link Page} of {@link GroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupQueryService extends QueryService<Group> {

    private final Logger log = LoggerFactory.getLogger(GroupQueryService.class);

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    public GroupQueryService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    /**
     * Return a {@link List} of {@link GroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupDTO> findByCriteria(GroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Group> specification = createSpecification(criteria);
        return groupMapper.toDto(groupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupDTO> findByCriteria(GroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Group> specification = createSpecification(criteria);
        return groupRepository.findAll(specification, page).map(groupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Group> specification = createSpecification(criteria);
        return groupRepository.count(specification);
    }

    /**
     * Function to convert {@link GroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Group> createSpecification(GroupCriteria criteria) {
        Specification<Group> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Group_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Group_.name));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Group_.password));
            }
            if (criteria.getGroupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getGroupId(), root -> root.join(Group_.group, JoinType.LEFT).get(Customer_.id))
                    );
            }
        }
        return specification;
    }
}
