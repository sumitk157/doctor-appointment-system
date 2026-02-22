package com.example.doctorapp.controller;

import com.example.doctorapp.model.Appointment;
import com.example.doctorapp.model.Doctor;
import com.example.doctorapp.repository.AppointmentRepository;
import com.example.doctorapp.repository.DoctorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorDashboardController {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorDashboardController(DoctorRepository doctorRepository,
                                     AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/dashboard")
    public String doctorDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        Doctor doctor = doctorRepository.findByEmail(email).orElse(null);

        if (doctor == null) {
            model.addAttribute("hasDoctorProfile", false);
            model.addAttribute("appointments", List.of());
        } else {
            List<Appointment> myAppointments = appointmentRepository.findByDoctor(doctor);
            model.addAttribute("hasDoctorProfile", true);
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointments", myAppointments);
            model.addAttribute("appointmentCount", myAppointments.size());
        }

        model.addAttribute("username", email);
        return "doctor-dashboard";
    }
}
