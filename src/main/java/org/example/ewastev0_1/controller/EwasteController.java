package org.example.ewastev0_1.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ewastev0_1.dto.request.EwasteRequest;
import org.example.ewastev0_1.dto.response.EwasteResponse;
import org.example.ewastev0_1.services.Interface.EwasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/ewaste")
@Validated
public class EwasteController {

    private final EwasteService ewasteService;



    @GetMapping
    public ResponseEntity<List<EwasteResponse>> getAllEwaste() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EwasteResponse> getEwasteById(@PathVariable Long id) {
      return null;
    }


    @PostMapping
    public ResponseEntity<EwasteResponse> createEwaste(@Valid @RequestBody EwasteRequest ewasteDto) {
        EwasteResponse response = ewasteService.createEwaste(ewasteDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EwasteResponse> updateEwaste(@PathVariable Long id, @Valid @RequestBody EwasteRequest ewasteDto) {
        return null;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEwaste(@PathVariable Long id) {
        return null;
    }

}
