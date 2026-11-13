package com.example.hospital.controller;

import com.example.hospital.entity.Appointment;
import com.example.hospital.repository.AppointmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppointmentController {
    private final AppointmentRepository appointments; private final DoctorRepository doctors; private final PatientRepository patients;
    public AppointmentController(AppointmentRepository appointments, DoctorRepository doctors, PatientRepository patients){this.appointments=appointments;this.doctors=doctors;this.patients=patients;}
    @GetMapping("/appointments") public String list(Model model){model.addAttribute("appointments",appointments.findAll());model.addAttribute("doctors",doctors.findAll());model.addAttribute("patients",patients.findAll());return "appointments";}
    @PostMapping("/appointments/save") public String save(@RequestParam Long doctorId,@RequestParam Long patientId,@ModelAttribute Appointment appointment){
        var doctor=doctors.findById(doctorId).orElseThrow(()->new IllegalArgumentException("Doctor not found"));
        var patient=patients.findById(patientId).orElseThrow(()->new IllegalArgumentException("Patient not found"));
        appointment.setDoctor(doctor);appointment.setPatient(patient);appointment.setStatus("BOOKED");appointments.save(appointment);return "redirect:/appointments";
    }
    @PostMapping("/appointments/{id}/cancel") public String cancel(@PathVariable Long id){appointments.findById(id).ifPresent(a->{a.setStatus("CANCELLED");appointments.save(a);});return "redirect:/appointments";}
}
