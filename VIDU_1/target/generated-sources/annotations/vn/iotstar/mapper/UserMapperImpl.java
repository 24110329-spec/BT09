package vn.iotstar.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-22T09:51:26+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.roleId( entityRoleId( entity ) );
        userDTO.roleName( entityRoleName( entity ) );
        userDTO.id( entity.getId() );
        userDTO.email( entity.getEmail() );
        userDTO.fullName( entity.getFullName() );
        userDTO.enabled( entity.isEnabled() );
        userDTO.createdAt( entity.getCreatedAt() );

        userDTO.productCount( entity.getProducts() != null ? entity.getProducts().size() : 0 );

        return userDTO.build();
    }

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.email( dto.getEmail() );
        user.fullName( dto.getFullName() );
        user.enabled( dto.isEnabled() );
        user.createdAt( dto.getCreatedAt() );

        return user.build();
    }

    private Long entityRoleId(User user) {
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getId();
    }

    private String entityRoleName(User user) {
        Role role = user.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getName();
    }
}
