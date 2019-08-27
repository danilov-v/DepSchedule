package com.varb.schedule.security.token;

import com.varb.schedule.exception.ServiceException;
import com.varb.schedule.security.config.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("security")
@Service
@RequiredArgsConstructor
public class UUIDTokenService implements TokenService {
    private Map<UUID, String> tokens = new ConcurrentHashMap<>();
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void validate(String token) {
        //nothing to do
    }

    @Override
    public UserDetails getUserDetails(String token) {
        return userDetailsServiceImpl.loadUserByUsername(
                Optional.ofNullable(tokens.get(UUID.fromString(token)))
                        .orElseThrow(() -> new ServiceException("Не валидная сессия (token="+token+")", HttpStatus.FORBIDDEN))
        );
    }

    @Override
    public String getNewToken(UserDetails user) {
        UUID uuid = UUID.randomUUID();
        tokens.put(uuid, user.getUsername());
        return uuid.toString();
    }

    @Override
    public void expireToken(String token) {
        tokens.remove(UUID.fromString(token));
    }
}
