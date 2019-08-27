package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.UserApi;
import com.varb.schedule.buisness.logic.service.UserService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.UserPostDto;
import com.varb.schedule.buisness.models.dto.UserPutDto;
import com.varb.schedule.buisness.models.dto.UserResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import com.varb.schedule.security.config.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Profile("security")
@ApiController
@RequiredArgsConstructor
public class UserApiImpl implements UserApi {
    private final UserService userService;
    private final ModelMapperCustomize modelMapper;
    private final Principal principal;

    @Override
    @Secured(PrivilegeEnum.Code.SUPER)
    public ResponseEntity<UserResponseDto> register(@Valid UserPostDto userPostDto) {
        if (1 == 1) //Пока что нет необходимости в функции регистрации
            throw new ServiceException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);

        return ResponseEntity.ok(userService.register(userPostDto));
    }

    @Override
    public ResponseEntity<UserResponseDto> login(@NotNull @Valid String username, @NotNull @Valid String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }

    @Secured(PrivilegeEnum.Code.BASE)
    @Override
    public ResponseEntity<Void> logout() {
        userService.logout(principal.getUserName());
        return ResponseEntity.ok().build();
    }

    @Override
    @Secured(PrivilegeEnum.Code.SUPER)
    public ResponseEntity<UserResponseDto> userPut(String login, @Valid UserPutDto userPutDto) {
        if (1 == 1) //Пока что нет необходимости в функции изменения пользователя
            throw new ServiceException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);

        return ResponseEntity.ok(
                modelMapper.map(userService.update(login, userPutDto), UserResponseDto.class));
    }

    @Override
    @Secured(PrivilegeEnum.Code.SUPER)
    public ResponseEntity<Void> userDelete(String login) {
        if (1 == 1) //Пока что нет необходимости в функции удаления пользователя
            throw new ServiceException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);

        userService.delete(login);
        return ResponseEntity.ok().build();
    }
}
