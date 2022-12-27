package uz.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import uz.domain.Customer;
import uz.domain.Role;
import uz.service.dto.CustomerDTO;
import uz.service.dto.RoleDTO;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {
    @Mapping(target = "customers", source = "customers", qualifiedByName = "customerIdSet")
    RoleDTO toDto(Role s);

    @Mapping(target = "removeCustomer", ignore = true)
    Role toEntity(RoleDTO roleDTO);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("customerIdSet")
    default Set<CustomerDTO> toDtoCustomerIdSet(Set<Customer> customer) {
        return customer.stream().map(this::toDtoCustomerId).collect(Collectors.toSet());
    }
}
