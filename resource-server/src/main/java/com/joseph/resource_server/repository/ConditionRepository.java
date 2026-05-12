package com.joseph.resource_server.repository;

import com.joseph.resource_server.model.Condition;
import com.joseph.resource_server.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConditionRepository extends JpaRepository<Condition, UUID> {
    List<Condition> findByPatient(Patient patient);
}
