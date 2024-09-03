package com.dev.dtoClasses;

import lombok.Data;

@Data
public class MeetingDetailsDTO {
  private Long id;
  private String venue;
  private String theme;
  private LocalDateTime dateTime;
  private List<RolesTakenDTO> roles;
}