package com.kg.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kg.entity.Patient;
import com.kg.modal.AnalyticsDTO;
import com.kg.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	

	public List<Patient> findAll() {
		return patientRepository.findAll();
	}

	public void save(Patient patient) {
		patientRepository.save(patient);
	}

	public List<Patient> findPatientsByUsername(String username) {
		return patientRepository.findByUserUsername(username);
	}
	public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    public AnalyticsDTO getPatientAnalytics() {
        List<Patient> patients = patientRepository.findAll();
        int totalPatients = patients.size();
        int malePatients = 0;
        int femalePatients = 0;
        int otherPatients = 0;

        // Initialize age distribution (assuming age groups: 0-9, 10-19, ..., 90-99, 100+)
        int[] ageDistribution = new int[11]; // 0-9, 10-19, ..., 90-99, 100+

        for (Patient patient : patients) {
            // Count gender distribution
            switch (patient.getGender()) {
                case "Male":
                    malePatients++;
                    break;
                case "Female":
                    femalePatients++;
                    break;
                default:
                    otherPatients++;
                    break;
            }

            // Calculate age
            LocalDate now = LocalDate.now();
            int age = Period.between(patient.getDateOfBirth(), now).getYears();
            if (age < 10) {
                ageDistribution[0]++;
            } else if (age < 20) {
                ageDistribution[1]++;
            } else if (age < 30) {
                ageDistribution[2]++;
            } else if (age < 40) {
                ageDistribution[3]++;
            } else if (age < 50) {
                ageDistribution[4]++;
            } else if (age < 60) {
                ageDistribution[5]++;
            } else if (age < 70) {
                ageDistribution[6]++;
            } else if (age < 80) {
                ageDistribution[7]++;
            } else if (age < 90) {
                ageDistribution[8]++;
            } else if (age < 100) {
                ageDistribution[9]++;
            } else {
                ageDistribution[10]++; // 100+
            }
        }

        // Create and populate the AnalyticsDTO object
        AnalyticsDTO analyticsDTO = new AnalyticsDTO();
        analyticsDTO.setTotalPatients(totalPatients);
        analyticsDTO.setGenderDistribution(new int[]{malePatients, femalePatients, otherPatients});
        analyticsDTO.setAgeDistribution(ageDistribution);

        return analyticsDTO;
    }
}
