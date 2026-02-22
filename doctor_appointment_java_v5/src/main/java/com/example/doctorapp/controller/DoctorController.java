package com.example.doctorapp.controller;

import com.example.doctorapp.model.Doctor;
import com.example.doctorapp.repository.DoctorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors";
    }

    @PostMapping
    public String addDoctor(@RequestParam String name,
                            @RequestParam String specialization,
                            @RequestParam String phone,
                            @RequestParam String timing,
                            @RequestParam(required = false) String email) {
        Doctor doctor = new Doctor(name, specialization, phone, timing, email);
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }
}
