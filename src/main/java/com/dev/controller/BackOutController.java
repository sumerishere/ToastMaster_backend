package com.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entity.BackOut;
import com.dev.service.impl.BackOutSrviceImpl;

@RestController
@RequestMapping("/backout")
public class BackOutController {
    
    @Autowired
    BackOutSrviceImpl backOutSrviceImpl;

    @PostMapping()
    public ResponseEntity<BackOut> saveBackOutDetails(@RequestBody BackOut backOut){
        return backOutSrviceImpl.saveBackOut(backOut);
    }
}
