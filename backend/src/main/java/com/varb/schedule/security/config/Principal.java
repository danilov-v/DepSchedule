package com.varb.schedule.security.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Profile("security")
@Component
public class Principal {
    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String getUserIpAddr() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();
    }

    public boolean requestIsNotFromLocalhost(){
        return !((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr().equals("0:0:0:0:0:0:0:1");
    }

    public String getToken(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getHeader("Authorization").substring(7);
    }


}