package org.project.admin.mapper;

import org.project.admin.dto.request.UserRequest;
import org.project.admin.dto.response.UserResponse;
import org.project.admin.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest dto);

    UserResponse toResponse(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserRequest dto, @MappingTarget User entity);
}
