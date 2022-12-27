package uz.service.mapper;

import org.mapstruct.*;
import uz.domain.Customer;
import uz.domain.Group;
import uz.service.dto.CustomerDTO;
import uz.service.dto.GroupDTO;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "group", source = "group", qualifiedByName = "groupId")
    CustomerDTO toDto(Customer s);

    @Named("groupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroupDTO toDtoGroupId(Group group);
}
