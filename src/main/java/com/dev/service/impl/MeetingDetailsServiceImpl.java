package com.dev.service.impl;

import java.util.List;
import java.util.Optional;

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

        List<RolesTaken> rolesTakens = meetingDetails.getRoles();

        // Check if rolesTakens is not null and iterate only if it's not null
        if (rolesTakens != null) {
            for (RolesTaken rolesTaken : rolesTakens) {
                rolesTaken.setMemberDetails(memberDetailsRepository.findById(rolesTaken.getMemberDetails().getId()).orElseThrow());
                rolesTaken.setMeetingDetails(meetingDetails2);
                rolesTakenRepository.save(rolesTaken);
            }
        }

        return new ResponseEntity<>(meetingDetails, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<List<MeetingDetails>> getAllMeetingDetails() {
       List<MeetingDetails> meetingDetails = meetingDetailsRepository.findAll();
       if(meetingDetails==null){
            throw new MeetingDetailsNotFoundException("Meeting details not present in database");
       }
       return new ResponseEntity<>(meetingDetails,HttpStatus.ACCEPTED);
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