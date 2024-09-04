package com.dev.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dev.dtoClasses.MemberDTO;
import com.dev.entity.MemberDetails;
import com.dev.entity.MemberShip;
import com.dev.exception.InvalidEmailAddressException;
import com.dev.exception.InvalidPhoneNumberException;
import com.dev.exception.MemberDetailsNotFoundException;
import com.dev.repository.MemberDetailsRepository;
import com.dev.service.MemberDetailsService;
import com.dev.validations.ValidationCheck;

@Service
public class MemberDetailsServiceImpl implements MemberDetailsService,ValidationCheck {

    @Autowired
    MemberDetailsRepository memberDetailsRepository;

    
    @Override
    public ResponseEntity<MemberDTO> saveMemberDetails(MemberDTO memberDTO) {

        if (memberDTO != null) {
            // Validate phone number and email
            if (!phoneNumberValid(memberDTO.getPhoneNumber())) {
                throw new InvalidPhoneNumberException("Invalid Phone Number");
            }
            if (!emailValidate(memberDTO.getEmailAddress())) {
                throw new InvalidEmailAddressException("Invalid Email Address");
            }

            // Create and save MemberDetails entity
            MemberDetails memberDetails = new MemberDetails();
            memberDetails.setFirstName(memberDTO.getFirstName());
            memberDetails.setLastName(memberDTO.getLastName());
            memberDetails.setAddress(memberDTO.getAddress());
            memberDetails.setPhoneNumber(memberDTO.getPhoneNumber());
            memberDetails.setEmailAddress(memberDTO.getEmailAddress());
            memberDetails.setProfession(memberDTO.getProfession());
            memberDetails.setDateOfBirth(memberDTO.getDateOfBirth());
            memberDetails.setDateTime(LocalDateTime.now());
            
            System.out.println("Saving MemberShip...");

            // Create and save MemberShip entity
            if (memberDTO.getFees() > 0 && memberDTO.getStartDate() != null && memberDTO.getEndDate() != null) {
                MemberShip membership = new MemberShip();
                membership.setFees(memberDTO.getFees());
                membership.setStartDate(memberDTO.getStartDate());
                membership.setEndDate(memberDTO.getEndDate());
                membership.setIsActive(memberDTO.getIsActive());
                membership.setMemberDetails(memberDetails);  // Link the membership with memberDetails

                memberShipRepository.save(membership);
                System.out.println("MemberShip saved with ID: " + membership.getId());

                // Populate membership details back into the DTO
                memberDTO.setMembershipId(membership.getId());
            }

            // Set the member ID to the DTO for the response
            memberDTO.setId(memberDetails.getId());

            return new ResponseEntity<>(memberDTO, HttpStatus.CREATED);
        }
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }   
    
    @Override
    public List<MemberDetails> getAllMemberDetails() {
        return memberDetailsRepository.findAll();
    }

    @Override
    public ResponseEntity<Void> deleteMemberById(Long id) {
        if(memberDetailsRepository.findById(id).isEmpty()){
            throw new MemberDetailsNotFoundException("Memebr details with id " +id+ " is not present in database");
        }
        memberDetailsRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    public boolean emailValidate(String email){
        
        if(email==null || email.isEmpty()) return false;

        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }

    public boolean phoneNumberValid(String phoneNumber){
        if(phoneNumber.length()!=10){
            return false;
        }

        for(int i=0;i<phoneNumber.length();i++){
            if(!(phoneNumber.charAt(i)>='0' && phoneNumber.charAt(i)<='9')){
                return false;
            }
        }
        return true;
    }

    @Override
    public ResponseEntity<MemberDetails> updateMemberDetails(MemberDetails memberDetails) {
        Long id = memberDetails.getId();

        Optional<MemberDetails> memberDetails2 = memberDetailsRepository.findById(id);

        if(memberDetails2.isEmpty()){
            throw new MemberDetailsNotFoundException("MemberDetails with id "+id+" is not present in database");
        }

        MemberDetails existingMemberDetails = memberDetails2.get();

        if(memberDetails.getFirstName()!=null){
            existingMemberDetails.setFirstName(memberDetails.getFirstName());
        }
        if(memberDetails.getLastName()!=null){
            existingMemberDetails.setLastName(memberDetails.getLastName());
        }
        if(memberDetails.getAddress()!=null){
            existingMemberDetails.setAddress(memberDetails.getAddress());
        }
        if(memberDetails.getDateOfBirth()!=null){
            existingMemberDetails.setDateOfBirth(memberDetails.getDateOfBirth());
        }
        if(memberDetails.getEmailAddress()!=null){
            existingMemberDetails.setEmailAddress(memberDetails.getEmailAddress());
        }
        if(memberDetails.getPhoneNumber()!=null){
            existingMemberDetails.setPhoneNumber(memberDetails.getPhoneNumber());
        }
        if(memberDetails.getProfession()!=null){
            existingMemberDetails.setProfession(memberDetails.getProfession());
        }

        return new ResponseEntity<>(memberDetailsRepository.save(existingMemberDetails),HttpStatus.CREATED);
    }
    
}
