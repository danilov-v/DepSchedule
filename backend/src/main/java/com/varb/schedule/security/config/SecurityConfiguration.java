package com.varb.schedule.security.config;

import com.varb.schedule.exception.ServiceException;
import com.varb.schedule.exception.handlers.ExceptionResolver;
import com.varb.schedule.security.token.TokenRequestFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Slf4j
@Profile("security")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenRequestFilter tokenRequestFilter;
    private final PasswordEncoder passwordEncoder;
    private final ExceptionResolver exceptionResolver;

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/**"));

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                //.exceptionHandling().authenticationEntryPoint(forbiddenEntryPoint())
                //.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // this particular request need to be authenticated
                .authorizeRequests()
                //.requestMatchers(PROTECTED_URLS).authenticated()
                //dont authenticate all other requests
                .anyRequest().permitAll().and()
                // Add a filter to validate the tokens with every request
                .addFilterBefore(tokenRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/console/**",
                        "/swagger-ui.html", "/h2-console/**/**");
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, exception) -> {
            log.error("", exception);
            ServiceException serviceException = new ServiceException(exception, HttpStatus.FORBIDDEN);
            exceptionResolver.resolve(serviceException, request, response);
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, exception) -> {
            ServiceException serviceException = new ServiceException(exception, HttpStatus.UNAUTHORIZED);
            exceptionResolver.resolve(serviceException, request, response);
        };
    }

}
