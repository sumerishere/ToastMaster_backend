package com.dev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
// import lombok.Getter;
// import lombok.Setter;

@Entity
@Data
@Table(name = "role_taken")
public class RolesTaken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "member_details_id")
    private MemberDetails memberDetails;

    @ManyToOne
    @JoinColumn(name = "meeting_details_id")
    @JsonIgnore   //use to avoid circular loop or infinite cycle
    private MeetingDetails meetingDetails;

    @JsonProperty("isAvailableRole")
    private boolean isAvailableRole;
}