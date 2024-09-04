package com.dev.dtoClasses;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO {

    // MemberDetails fields
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String profession;
    private String dateOfBirth;
    private LocalDateTime dateTime;

    // MemberShip fields
    private Long membershipId;
    private int fees;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;

}
