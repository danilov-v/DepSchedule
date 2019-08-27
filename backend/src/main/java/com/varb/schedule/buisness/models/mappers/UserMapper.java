package com.varb.schedule.buisness.models.mappers;


import com.varb.schedule.buisness.models.business.RoleEnum;
import com.varb.schedule.buisness.models.dto.RoleDto;
import com.varb.schedule.buisness.models.dto.UserPostDto;
import com.varb.schedule.buisness.models.dto.UserPutDto;
import com.varb.schedule.buisness.models.dto.UserResponseDto;
import com.varb.schedule.buisness.models.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("security")
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    void init() {

        AbstractConverter<String, String> passEncode = new AbstractConverter<>() {
            @Override
            protected String convert(String pass) {
                return passwordEncoder.encode(pass);
            }
        };

        AbstractConverter<RoleEnum, RoleDto> toRoleDto = new AbstractConverter<>() {
            @Override
            protected RoleDto convert(RoleEnum source) {
                return RoleDto.valueOf(source.toString());
            }
        };

        modelMapper
                .typeMap(UserPostDto.class, User.class)
                .addMappings(mapper -> mapper
                        .using(passEncode)
                        .map(UserPostDto::getPassword, User::setPassword));

        modelMapper
                .typeMap(UserPutDto.class, User.class)
                .addMappings(mapper -> mapper
                        .using(passEncode)
                        .map(UserPutDto::getPassword, User::setPassword));

        modelMapper
                .typeMap(User.class, UserResponseDto.class)
                .addMappings(mapper -> mapper
                        .using(toRoleDto)
                        .map(User::getRole, UserResponseDto::setRole));

    }

}
