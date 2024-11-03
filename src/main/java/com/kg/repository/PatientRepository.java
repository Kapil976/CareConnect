package com.kg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kg.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByUserUsername(String username);
}

