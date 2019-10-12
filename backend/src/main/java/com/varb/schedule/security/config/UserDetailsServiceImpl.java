package com.varb.schedule.security.config;

import com.varb.schedule.buisness.logic.repository.UserRepository;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.business.RoleEnum;
import com.varb.schedule.buisness.models.entity.User;
import com.varb.schedule.exception.WebApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Profile("security")
@Transactional
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
   private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.findById(login)
                .orElseThrow(() -> new WebApiException("Пользователь не найден в системе (login = " + login + ")",
                        HttpStatus.UNAUTHORIZED));

        return mapToUserDetails(user);
    }

    public UserDetails mapToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRole()))
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(RoleEnum role) {
        return getPrivileges(role).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private Set<String> getPrivileges(RoleEnum role) {
        return role.getPrivileges().stream()
                .map(PrivilegeEnum::toString)
                .collect(Collectors.toSet());
    }

}
