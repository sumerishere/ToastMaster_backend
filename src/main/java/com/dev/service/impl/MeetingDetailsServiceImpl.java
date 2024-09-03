package com.dev.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dev.dtoClasses.MeetingDetailsDTO;
import com.dev.dtoClasses.RolesTakenDTO;
import com.dev.entity.MeetingDetails;
import com.dev.entity.RolesTaken;
// import com.dev.entity.Roles;
import com.dev.exception.MeetingDetailsNotFoundException;
import com.dev.exception.RoleNotFoundException;
import com.dev.repository.MeetingDetailsRepository;
import com.dev.repository.MemberDetailsRepository;
import com.dev.repository.RolesTakenRepository;
// import com.dev.repository.RolesRepository;
import com.dev.service.MeetingDetailsService;


@Service
public class MeetingDetailsServiceImpl implements MeetingDetailsService {

    // @Autowired
    // RolesRepository rolesRepository;

    @Autowired
    MeetingDetailsRepository meetingDetailsRepository;

    @Autowired
    RolesTakenRepository rolesTakenRepository;

    @Autowired
    MemberDetailsRepository memberDetailsRepository;

    @Override
    public ResponseEntity<MeetingDetails> saveMeetingDetails(MeetingDetails meetingDetails) {

        MeetingDetails meetingDetails2 = new MeetingDetails();

        meetingDetails2.setVenue(meetingDetails.getVenue());
        meetingDetails2.setDateTime(meetingDetails.getDateTime());
        meetingDetails2.setTheme(meetingDetails.getTheme());
    
        // Save the meeting details first
        meetingDetails = meetingDetailsRepository.save(meetingDetails2);
    
        
        for (RolesTaken rolesTaken : meetingDetails.getRoles()) {

            if (rolesTaken.getMemberDetails() != null && rolesTaken.getMemberDetails().getId() != null) {

                rolesTaken.setMemberDetails(memberDetailsRepository.findById(rolesTaken.getMemberDetails().getId())
                        .orElseThrow(() -> new RuntimeException("Member not found")));

                rolesTaken.setMeetingDetails(meetingDetails2);
                rolesTakenRepository.save(rolesTaken);
            } 
            else {
                throw new IllegalArgumentException("MemberDetails or Member ID is missing in the request");
            }
        }
    
        return new ResponseEntity<>(meetingDetails, HttpStatus.CREATED);
    }
    

    public List<MeetingDetailsDTO> convertToDTO(List<MeetingDetails> meetingDetailsList) {
        return meetingDetailsList.stream().map(meetingDetails -> {
            MeetingDetailsDTO dto = new MeetingDetailsDTO();
            dto.setId(meetingDetails.getId());
            dto.setVenue(meetingDetails.getVenue());
            dto.setTheme(meetingDetails.getTheme());
            dto.setDateTime(meetingDetails.getDateTime());
            dto.setRoles(
                meetingDetails.getRoles().stream().map(role -> {
                    RolesTakenDTO roleDto = new RolesTakenDTO();
                    roleDto.setId(role.getId());
                    roleDto.setRoleName(role.getRoleName());
                    roleDto.setAvailableRole(role.isAvailableRole());
                    return roleDto;
                }).collect(Collectors.toList())
            );
            return dto;
        }).collect(Collectors.toList());
    }



    @Override
    public ResponseEntity<List<MeetingDetailsDTO>> getAllMeetingDetails() {

        List<MeetingDetails> meetingDetails = meetingDetailsRepository.findAll();
        
        if (meetingDetails.isEmpty()) {
            throw new MeetingDetailsNotFoundException("Meeting details not present in the database");
        }

        List<MeetingDetailsDTO> meetingDetailsDTO = convertToDTO(meetingDetails);

        return new ResponseEntity<>(meetingDetailsDTO, HttpStatus.ACCEPTED);
    }


    @Override
    public ResponseEntity<MeetingDetails> updateMeetingDetails(MeetingDetails meetingDetails) {
        Long id = meetingDetails.getId();
        Optional<MeetingDetails> meetingDetails1 = meetingDetailsRepository.findById(id);
        if(meetingDetails1.isEmpty()){
            throw new MeetingDetailsNotFoundException("Meeting Details with id "+id+" is not present into database");
        }

        MeetingDetails existingMeetingDetails = meetingDetails1.get();

        existingMeetingDetails.setId(meetingDetails.getId());
        existingMeetingDetails.setDateTime(meetingDetails.getDateTime());
        existingMeetingDetails.setVenue(meetingDetails.getVenue());
        //existingMeetingDetails.setSpeech(meetingDetails.getSpeech());
        //existingMeetingDetails.setRoles(meetingDetails.getRoles());

        return new ResponseEntity<>(meetingDetailsRepository.save(existingMeetingDetails),HttpStatus.CREATED);


    }

    @Override
    public ResponseEntity<Void> deleteMeetingDetails(Long id) {
        if(meetingDetailsRepository.findById(id).isEmpty()){
            throw new MeetingDetailsNotFoundException("Meeting details with id "+id+" is not present in database");
        }

        meetingDetailsRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    
}
