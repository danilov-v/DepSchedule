package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.LoginApi;
import com.varb.schedule.buisness.logic.controllers.api.LogoutApi;
import com.varb.schedule.buisness.logic.controllers.api.UserApi;
import com.varb.schedule.buisness.logic.service.UserService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.UserReqDto;
import com.varb.schedule.buisness.models.dto.UserReqExtDto;
import com.varb.schedule.buisness.models.dto.UserResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.WebApiException;
import com.varb.schedule.security.config.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;

@Profile("security")
@ApiController
@RequiredArgsConstructor
public class UserApiImpl implements UserApi, LoginApi, LogoutApi {
    private final UserService userService;
    private final ModelMapperCustomize modelMapper;
    private final Principal principal;

    @Override
    public ResponseEntity<UserResponseDto> login(@Valid UserReqDto userBaseReqDto) {
        return ResponseEntity.ok(userService.login(userBaseReqDto.getLogin(), userBaseReqDto.getPassword()));
    }

    @Secured(PrivilegeEnum.Code.BASE)
    @Override
    public ResponseEntity<Void> logout() {
        userService.logout(principal.getUserName());
        return ResponseEntity.ok().build();
    }

    @Override
    @Secured(PrivilegeEnum.Code.SUPER)
    public ResponseEntity<UserResponseDto> register(@Valid UserReqExtDto userPostDto) {
        if (1 == 1) //Пока что нет необходимости в функции регистрации
            throw new WebApiException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);

        return ResponseEntity.ok(userService.register(userPostDto));
    }

    @Override
    @Secured(PrivilegeEnum.Code.SUPER)
    public ResponseEntity<UserResponseDto> userPatch(String login, @Valid UserReqExtDto userPutDto) {
        if (1 == 1) //Пока что нет необходимости в функции изменения пользователя
            throw new WebApiException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);

        return ResponseEntity.ok(
                modelMapper.map(userService.update(login, userPutDto), UserResponseDto.class));
    }

    @Override
    @Secured(PrivilegeEnum.Code.SUPER)
    public ResponseEntity<Void> userDelete(String login) {
        if (1 == 1) //Пока что нет необходимости в функции удаления пользователя
            throw new WebApiException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);

        userService.delete(login);
        return ResponseEntity.ok().build();
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }
}
