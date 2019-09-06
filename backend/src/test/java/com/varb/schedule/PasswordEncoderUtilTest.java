package com.varb.schedule;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtilTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encoded = encoder.encode("12345");//SUPERUSER password
        System.out.println(encoded);

        encoded = encoder.encode("12345");//User password
        System.out.println(encoded);
    }
}