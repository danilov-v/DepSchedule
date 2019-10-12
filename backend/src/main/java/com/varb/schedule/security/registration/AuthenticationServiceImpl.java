package com.varb.schedule.security.registration;

import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.business.RoleEnum;
import com.varb.schedule.exception.WebApiException;
import com.varb.schedule.security.config.Principal;
import com.varb.schedule.security.config.UserDetailsServiceImpl;
import com.varb.schedule.security.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Profile("security")
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final Principal principal;
    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;

    private static final String INVALID_PASSWORD = "INVALID_PASSWORD";
    private static final String IP_ADDRESS_MISMATCH = "IP_ADDRESS_MISMATCH";


    public String register(UserDetails user) {
        checkIpAddrForSuperUser(user.getAuthorities());
        return tokenService.getNewToken(user);
    }

    public String login(String login, String password) {
        UserDetails user = userDetailsService.loadUserByUsername(login);

        checkPassword(password, user.getPassword());
        checkIpAddrForSuperUser(user.getAuthorities());

        return tokenService.getNewToken(user);
    }

    public void logout(String login) {
        tokenService.expireToken(principal.getToken());
    }

    /**
     * @param password        the password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     */
    private void checkPassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            String message = "Неправильный пароль";
            throw new WebApiException(message, message, INVALID_PASSWORD, HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * If the role is equal to the superuser,
     * then check that the ip address of the user is equal to localhost
     */
    private void checkIpAddrForSuperUser(Collection<? extends GrantedAuthority> authorities) {
        if (isContainsSuperRole(authorities) && principal.requestIsNotFromLocalhost()) {
            String devMessage = RoleEnum.SUPERUSER.toString() + " может логиниться" +
                    " только с компьютера на котором развёрнут сервер";
            String userMessage = "Отказано в доступе. Вы можете логиниться под этим пользователем" +
                    " только с компьютера на котором развёрнут сервер";

            throw new WebApiException(devMessage, userMessage, IP_ADDRESS_MISMATCH, HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean isContainsSuperRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(s -> s.equals(PrivilegeEnum.Code.SUPER));
    }
}
