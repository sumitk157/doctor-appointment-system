package com.example.doctorapp.controller;

import com.example.doctorapp.repository.AppointmentRepository;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.PatientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public DashboardController(DoctorRepository doctorRepository,
                               PatientRepository patientRepository,
                               AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, Authentication authentication) {
        boolean isDoctor = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_DOCTOR"));

        if (isDoctor) {
            return "redirect:/doctor/dashboard";
        }

        model.addAttribute("doctorCount", doctorRepository.count());
        model.addAttribute("patientCount", patientRepository.count());
        model.addAttribute("appointmentCount", appointmentRepository.count());
        model.addAttribute("username", authentication.getName());
        return "dashboard";
    }
}
