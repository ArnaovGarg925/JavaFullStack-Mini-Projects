package com.example.hospital.controller;

import com.example.hospital.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final DepartmentRepository departments; private final DoctorRepository doctors; private final PatientRepository patients; private final AppointmentRepository appointments;
    public HomeController(DepartmentRepository departments,DoctorRepository doctors,PatientRepository patients,AppointmentRepository appointments){this.departments=departments;this.doctors=doctors;this.patients=patients;this.appointments=appointments;}
    @GetMapping("/") public String home(Model model){model.addAttribute("departmentCount",departments.count());model.addAttribute("doctorCount",doctors.count());model.addAttribute("patientCount",patients.count());model.addAttribute("appointmentCount",appointments.count());return "index";}
}
