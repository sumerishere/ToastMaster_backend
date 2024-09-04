package com.dev.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MeetingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venue")
    private String venue;

    @Column(name = "theme")
    private String theme;
    
    @Column(name="meeting_date")
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "meetingDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonManagedReference 
    private List<RolesTaken> roles = new ArrayList<>(); // Initialize the list;
}