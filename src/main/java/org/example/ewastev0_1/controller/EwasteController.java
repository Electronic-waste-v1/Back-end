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
        List<EwasteResponse> ewasteResponse=ewasteService.getAllEwastes();
        return ResponseEntity.status(HttpStatus.OK).body(ewasteResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EwasteResponse> getEwasteById(@PathVariable Integer id) {
        EwasteResponse ewasteResponse=ewasteService.getEwasteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ewasteResponse)  ;
    }


    @PostMapping
    public ResponseEntity<EwasteResponse> createEwaste(@Valid @RequestBody EwasteRequest ewasteDto) {
        EwasteResponse response = ewasteService.createEwaste(ewasteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EwasteResponse> updateEwaste(@PathVariable Integer id, @Valid @RequestBody EwasteRequest ewasteDto) {
        EwasteResponse response = ewasteService.updateEwaste(id, ewasteDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEwaste(@PathVariable Integer id) {
        ewasteService.deleteEwaste(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
