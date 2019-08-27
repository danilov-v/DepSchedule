package com.varb.schedule.security.token;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Creates and validates credentials.
 */
public interface TokenService{
  /**
   * Checks the validity of the given credentials and get {@link UserDetails}.
   */
  default UserDetails verify(String token){
    validate(token);
    return getUserDetails(token);
  }
  /**
   * Checks the validity of the given credentials.
   */
  void validate(String token);

  UserDetails getUserDetails(String token);

  String getNewToken(UserDetails user);

  void expireToken(String token);
}
