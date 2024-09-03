package com.dev.service;

import org.springframework.http.ResponseEntity;

import com.dev.entity.BackOut;

public interface BackOutService {
    
    public ResponseEntity<BackOut> saveBackOut(BackOut backOut);
}
