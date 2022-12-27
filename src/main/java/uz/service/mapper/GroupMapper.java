package uz.service.mapper;

import org.mapstruct.*;
import uz.domain.Group;
import uz.service.dto.GroupDTO;

/**
 * Mapper for the entity {@link Group} and its DTO {@link GroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {}
