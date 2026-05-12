package com.joseph.resource_server.controller;

import com.joseph.resource_server.dto.PatientResponse;
import com.joseph.resource_server.model.Patient;
import com.joseph.resource_server.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;


    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientResponse>> searchPatients(@RequestParam String name){
        List<PatientResponse> patients = patientService.searchPatients(name);
        return ResponseEntity.ok(patients);
    }
}
