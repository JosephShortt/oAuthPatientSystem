package com.joseph.resource_server.dto;

import com.joseph.resource_server.model.Condition;
import com.joseph.resource_server.model.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PatientResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String city;
    private List<String> conditions;

    public PatientResponse(Patient patient, List<Condition> conditions) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.address = patient.getAddress();
        this.city = patient.getCity();
        this.conditions = conditions.stream()
                .map(Condition::getDescription)
                .toList();
    }

    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public List<String> getConditions() { return conditions; }
}
