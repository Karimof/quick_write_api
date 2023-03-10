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
import uz.domain.Text;
import uz.repository.TextRepository;
import uz.service.criteria.TextCriteria;
import uz.service.dto.TextDTO;
import uz.service.mapper.TextMapper;

/**
 * Service for executing complex queries for {@link Text} entities in the database.
 * The main input is a {@link TextCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TextDTO} or a {@link Page} of {@link TextDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TextQueryService extends QueryService<Text> {

    private final Logger log = LoggerFactory.getLogger(TextQueryService.class);

    private final TextRepository textRepository;

    private final TextMapper textMapper;

    public TextQueryService(TextRepository textRepository, TextMapper textMapper) {
        this.textRepository = textRepository;
        this.textMapper = textMapper;
    }

    /**
     * Return a {@link List} of {@link TextDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TextDTO> findByCriteria(TextCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Text> specification = createSpecification(criteria);
        return textMapper.toDto(textRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TextDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TextDTO> findByCriteria(TextCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Text> specification = createSpecification(criteria);
        return textRepository.findAll(specification, page).map(textMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TextCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Text> specification = createSpecification(criteria);
        return textRepository.count(specification);
    }

    /**
     * Function to convert {@link TextCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Text> createSpecification(TextCriteria criteria) {
        Specification<Text> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Text_.id));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Text_.text));
            }
        }
        return specification;
    }
}
