package com.kg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kg.config.SecurityUtil;
import com.kg.modal.AnalyticsDTO;
import com.kg.service.PatientService;

@Controller
public class DashboardController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String username = SecurityUtil.getCurrentUsername();
        model.addAttribute("username", username);
        return "dashboard"; // Ensure this matches your login.html file name
    }

    @GetMapping("/reports")
    public String showReports() {
        return "reports"; // This should point to your Thymeleaf template for reports
    }

    @GetMapping("/api/patient-analytics")
    @ResponseBody
    public AnalyticsDTO getPatientAnalytics() {
        return patientService.getPatientAnalytics();
    }
}
