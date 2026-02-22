package com.example.doctorapp.controller;

import com.example.doctorapp.model.Patient;
import com.example.doctorapp.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patients";
    }

    @PostMapping
    public String addPatient(@RequestParam String name,
                             @RequestParam String phone,
                             @RequestParam String gender) {
        Patient p = new Patient(name, phone, gender);
        patientRepository.save(p);
        return "redirect:/patients";
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }

    @PostMapping("/{id}/face")
    public String saveFaceForPatient(@PathVariable Long id,
                                     @RequestParam("imageData") String imageData) {
        Optional<Patient> opt = patientRepository.findById(id);
        if (opt.isPresent()) {
            Patient p = opt.get();
            p.setFaceImageBase64(imageData);
            patientRepository.save(p);
        }
        return "redirect:/patients";
    }
}
