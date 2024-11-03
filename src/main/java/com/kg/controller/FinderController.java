package com.kg.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kg.entity.Patient;
import com.kg.entity.User;
import com.kg.modal.UserDTO;
import com.kg.repository.PatientRepository;
import com.kg.repository.UserRepository;

@Controller
public class FinderController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PatientRepository patientRepository;

	@GetMapping("/allUser")
	@ResponseBody
	public List<UserDTO> showAllUsers() {
		List<User> users = userRepository.findAll();

		// Convert List<User> to List<UserDTO>, including the password
		return users.stream()
				.map(user -> new UserDTO(
						user.getId(),
						user.getUsername(),
						user.getEmail(),
						user.getPhone(),
						user.getPassword()))
				.collect(Collectors.toList());
	}

	@GetMapping("/allPatients")
	@ResponseBody
	public List<Patient> showAllPatients() {
		return patientRepository.findAll(); // Retrieves all patients from the database
	}
}
