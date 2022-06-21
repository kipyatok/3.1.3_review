package com.example.demo.converter;

import com.example.demo.model.User;
import com.example.demo.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserConverter extends Converter<UserEntity, User> {
    @Override
    List<User> convert(List<UserEntity> entities);

    @Override
    UserEntity convert(User bean);

    @Override
    User convert(UserEntity entity);
}
