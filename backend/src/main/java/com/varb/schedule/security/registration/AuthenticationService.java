package com.varb.schedule.security.registration;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

  /**
   * Logs in with the given {@code username} and {@code password}.
   * @return token
   */
  String login(String username, String password);

  /**
   * Logs in with the given {@code username} and {@code password}.
   * @return token
   */
  String register(UserDetails user);

  /**
   * Logs out the given input {@code user}.
   */
  void logout(String login);
}
