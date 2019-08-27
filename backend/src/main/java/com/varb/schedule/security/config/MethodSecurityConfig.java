package com.varb.schedule.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Profile("security")
@Configuration
@EnableGlobalMethodSecurity(
  prePostEnabled = false,
  securedEnabled = true, 
  jsr250Enabled = false)
public class MethodSecurityConfig 
  extends GlobalMethodSecurityConfiguration {
}