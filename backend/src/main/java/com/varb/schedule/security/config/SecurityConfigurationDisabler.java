package com.varb.schedule.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Disabled security by default
 */
@Slf4j
@Configuration
@Profile("!security")
@EnableWebSecurity
public class SecurityConfigurationDisabler extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/**");
    }

}
