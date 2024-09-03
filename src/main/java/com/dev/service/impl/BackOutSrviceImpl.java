package com.dev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dev.entity.BackOut;
import com.dev.entity.RolesTaken;
import com.dev.repository.BackOutRepository;
import com.dev.service.BackOutService;

@Service
public class BackOutSrviceImpl implements BackOutService {

    @Autowired
    RolesTakenServiceImpl rolesTakenServiceImpl;

    @Autowired
    BackOutRepository backOutRepository;

    @Override
    public ResponseEntity<BackOut> saveBackOut(BackOut backOut) {

        rolesTakenServiceImpl.deleteRoleNameAndMember(backOut.getRoleName());


       BackOut backOut2 = backOutRepository.save(backOut);

       return new ResponseEntity<>(backOut2,HttpStatus.CREATED);

        
    }
    
}
