package com.varb.schedule.security.token;

import com.varb.schedule.exception.WebApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Profile("security")
@Component
@RequiredArgsConstructor
public class TokenRequestFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        //request.getRemoteAddr()
        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null) {

            // Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            if (!requestTokenHeader.startsWith("Bearer "))
                throw new WebApiException("Token does not begin with 'Bearer '", HttpStatus.UNAUTHORIZED);

            String token = requestTokenHeader.substring(7);

            // Once we get the token validate it.
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // if token is valid configure Spring Security to manually set
                // authentication
                UserDetails userDetails = tokenService.verify(token);

                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()));
            }
        }

        chain.doFilter(request, response);
    }

}