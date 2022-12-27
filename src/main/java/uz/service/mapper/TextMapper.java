package uz.service.mapper;

import org.mapstruct.*;
import uz.domain.Text;
import uz.service.dto.TextDTO;

/**
 * Mapper for the entity {@link Text} and its DTO {@link TextDTO}.
 */
@Mapper(componentModel = "spring")
public interface TextMapper extends EntityMapper<TextDTO, Text> {}
