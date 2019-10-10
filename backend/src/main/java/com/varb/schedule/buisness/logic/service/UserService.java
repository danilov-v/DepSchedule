package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.models.dto.UserPostDto;
import com.varb.schedule.buisness.models.dto.UserPutDto;
import com.varb.schedule.buisness.models.dto.UserResponseDto;
import com.varb.schedule.buisness.models.entity.User;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.security.config.UserDetailsServiceImpl;
import com.varb.schedule.security.registration.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Profile("security")
@Transactional
@Service
@RequiredArgsConstructor
public class UserService extends AbstractService<User, String> {
    private final ModelMapperCustomize modelMapper;
    private final AuthenticationServiceImpl userAuthenticationService;
    private final UserDetailsServiceImpl userDetailsService;

    public UserResponseDto register(UserPostDto userPost) {
        User user = modelMapper.map(userPost, User.class);

        String token = userAuthenticationService.register(userDetailsService.mapToUserDetails(user));

        user = save(user);

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
        return userResponseDto.token(UUID.fromString(token));
    }

    public UserResponseDto login(String login, String password) {
        String token = userAuthenticationService.login(login, password);

        UserResponseDto userResponseDto = modelMapper.map(findById(login), UserResponseDto.class);
        return userResponseDto.token(UUID.fromString(token));
    }

    public void logout(String login) {
        userAuthenticationService.logout(login);
    }

    public User update(String login, UserPutDto userPut) {
        User user = findById(login);
        modelMapper.map(userPut, user);
        return user;
    }

    @Override
    protected String notFindMessage(String login) {
        return "Не найден пользователь (login=" + login + ")";
    }

}
