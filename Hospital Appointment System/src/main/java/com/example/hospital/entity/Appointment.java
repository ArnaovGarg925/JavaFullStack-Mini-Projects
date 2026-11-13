package com.example.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Doctor doctor;
    @ManyToOne(optional=false) private Patient patient;
    private LocalDateTime appointmentDate;
    private String reason;
    private String status = "BOOKED";
    public Appointment() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public Doctor getDoctor(){return doctor;} public void setDoctor(Doctor doctor){this.doctor=doctor;}
    public Patient getPatient(){return patient;} public void setPatient(Patient patient){this.patient=patient;}
    public LocalDateTime getAppointmentDate(){return appointmentDate;} public void setAppointmentDate(LocalDateTime date){this.appointmentDate=date;}
    public String getReason(){return reason;} public void setReason(String reason){this.reason=reason;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
    @Transient public String getDepartmentNames(){ return doctor == null || doctor.getDepartments() == null ? "—" : doctor.getDepartments().stream().map(Department::getName).collect(Collectors.joining(", ")); }
}
