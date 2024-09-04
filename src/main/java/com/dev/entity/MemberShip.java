package com.dev.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MemberShip {

    @Id
    @GeneratedValue
    private Long id;

    private int fees;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

    private Boolean isActive;

    // @OneToOne
    //@JoinColumn(name="member_details_id")
    //private MemberDetails memberDetails;

    // Bidirectional One-to-One relationship
    @OneToOne
    @JoinColumn(name="member_details_id", referencedColumnName = "id",cascade)
    private MemberDetails memberDetails; // Ensure this is correctly set up

    
}
