package com.joseph.resource_server.service;

import com.joseph.resource_server.dto.PatientResponse;
import com.joseph.resource_server.model.Patient;
import com.joseph.resource_server.repository.ConditionRepository;
import com.joseph.resource_server.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final ConditionRepository conditionRepository;

    public PatientService(PatientRepository patientRepository, ConditionRepository conditionRepository){
        this.patientRepository=patientRepository;
        this.conditionRepository=conditionRepository;
    }

    public List<PatientResponse> searchPatients(String name){
        List<Patient> patients = patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name,name);

        return patients.stream()
                .map(patient -> new PatientResponse(patient,conditionRepository.findByPatient(patient)))
                .toList();
    }
}
