package com.dev.dtoClasses;

import lombok.Data;

@Data
public class RolesTakenDTO {
  private Long id;
  private String roleName;
  private boolean isAvailableRole;
}