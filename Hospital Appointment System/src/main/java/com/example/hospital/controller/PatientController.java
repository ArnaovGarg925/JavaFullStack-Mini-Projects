package com.example.hospital.controller;

import com.example.hospital.entity.Patient;
import com.example.hospital.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {
    private final PatientRepository patients;
    public PatientController(PatientRepository patients){this.patients=patients;}
    @GetMapping("/patients") public String list(Model model){model.addAttribute("patients",patients.findAll());return "patients";}
    @PostMapping("/patients/save") public String save(@ModelAttribute Patient patient){patients.save(patient);return "redirect:/patients";}
}
