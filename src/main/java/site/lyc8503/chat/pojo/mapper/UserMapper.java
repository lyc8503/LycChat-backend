package site.lyc8503.chat.pojo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import site.lyc8503.chat.pojo.dto.UserDTO;
import site.lyc8503.chat.pojo.entity.UserEntity;
import site.lyc8503.chat.pojo.vo.session.PostSessionRequest;
import site.lyc8503.chat.pojo.vo.user.PostUserRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "passwordHash", expression = "java(cn.dev33.satoken.secure.BCrypt.hashpw(userDTO.getPassword()))")
    UserEntity userDTOToUserEntity(UserDTO userDTO);

    UserDTO postUserRequestToUserDTO(PostUserRequest request);
    UserDTO postSessionRequestToUserDTO(PostSessionRequest request);
}
