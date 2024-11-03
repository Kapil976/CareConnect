package com.kg.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kg.config.SecurityUtil;
import com.kg.entity.Patient;
import com.kg.entity.User;
import com.kg.service.PatientService;
import com.kg.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class PatientController {

	@Autowired
	private UserService userService;

	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping("/patients")
	public String listPatients(Model model) {

		String username = SecurityUtil.getCurrentUsername();
		List<Patient> patients = patientService.findPatientsByUsername(username);

		model.addAttribute("username", username);
		model.addAttribute("patients", patients);
		return "patients";
	}

	@GetMapping("/addPatient")
	public String showAddPatientForm(Model model) {
		Patient patient = new Patient(); // Create a new Patient object
		String username = SecurityUtil.getCurrentUsername();
		model.addAttribute("username", username);
		model.addAttribute("patient", patient); // Add it to the model
		return "add-patient"; // Return the view name
	}

	@PostMapping("/addPatient")
	public String savePatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "add-patient"; // Return to the form if there are validation errors
		}
		String username = SecurityUtil.getCurrentUsername();
		User currentUser = userService.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
		patient.setUser(currentUser);
		patient.setLastVisit(LocalDate.now());
		patientService.save(patient); // Save patient details
		return "redirect:/patients"; // Redirect after success
	}

	@GetMapping("/restrictedPage")
	public String restrictedPage(Model model, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies
		return "restrictedPage"; // Return the view name
	}

	@GetMapping("/getPatientData")
	@ResponseBody
	public ResponseEntity<Patient> getPatientData(@RequestParam Long id) {
		Optional<Patient> patientOptional = patientService.findById(id);
		if (patientOptional.isPresent()) {
			return ResponseEntity.ok(patientOptional.get());
		}
		return ResponseEntity.notFound().build(); // Return a 404 if not found
	}

	@PostMapping("/editPatient")
	public String updatePatient(@ModelAttribute Patient updatedPatient) {
		Optional<Patient> existingPatientOpt = patientService.findById(updatedPatient.getId());

		if (existingPatientOpt.isPresent()) {
			Patient existingPatient = existingPatientOpt.get();

			// Update only specific fields
			existingPatient.setName(updatedPatient.getName());
			existingPatient.setMedicalHistory(updatedPatient.getMedicalHistory());
			existingPatient.setLastVisit(LocalDate.now());
			// Save the updated patient details
			patientService.save(existingPatient);
		}

		return "redirect:/patients"; // Redirect to main page
	}

}
