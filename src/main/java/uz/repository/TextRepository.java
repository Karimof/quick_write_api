package uz.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.domain.Text;

/**
 * Spring Data JPA repository for the Text entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TextRepository extends JpaRepository<Text, Long>, JpaSpecificationExecutor<Text> {}
