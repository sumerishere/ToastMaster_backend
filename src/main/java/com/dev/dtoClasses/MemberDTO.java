package com.dev.dtoClasses;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MemberDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String profession;
    private String dateOfBirth;
    private LocalDateTime dateTime;

    private Long membershipId;
    private int fees;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;

    // Getters and setters
}
