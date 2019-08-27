package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets Role
 */
public enum RoleDto {
  
  SUPERUSER("superuser"),
  
  ADMIN("admin"),
  
  USER("user"),
  
  ANONYM("anonym");

  private String value;

  RoleDto(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static RoleDto fromValue(String value) {
    for (RoleDto b : RoleDto.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

