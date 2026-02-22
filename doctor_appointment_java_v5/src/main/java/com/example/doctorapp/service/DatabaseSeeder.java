package com.example.doctorapp.service;

import com.example.doctorapp.model.Doctor;
import com.example.doctorapp.model.Patient;
import com.example.doctorapp.model.Role;
import com.example.doctorapp.model.User;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.PatientRepository;
import com.example.doctorapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository,
                          DoctorRepository doctorRepository,
                          PatientRepository patientRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User(
                    "admin@example.com",
                    passwordEncoder.encode("admin123"),
                    "System Admin",
                    Role.ADMIN
            );
            userRepository.save(admin);

            User defaultPatientUser = new User(
                    "patient@example.com",
                    passwordEncoder.encode("patient123"),
                    "Default Patient",
                    Role.PATIENT
            );
            userRepository.save(defaultPatientUser);
        }

        if (doctorRepository.count() == 0) {
            Doctor d1 = new Doctor("Dr. Anuj Verma", "Cardiologist", "9990001111",
                    "10:00 AM - 1:00 PM", "anuj@hospital.com");
            Doctor d2 = new Doctor("Dr. Neha Sharma", "Dermatologist", "9990002222",
                    "2:00 PM - 5:00 PM", "neha@hospital.com");
            Doctor d3 = new Doctor("Dr. Rahul Singh", "Neurologist", "9990003333",
                    "6:00 PM - 9:00 PM", "rahul@hospital.com");
            doctorRepository.save(d1);
            doctorRepository.save(d2);
            doctorRepository.save(d3);

            // Create login accounts for doctors
            if (userRepository.findByEmail("anuj@hospital.com").isEmpty()) {
                userRepository.save(new User("anuj@hospital.com",
                        passwordEncoder.encode("doctor123"),
                        "Dr. Anuj Verma", Role.DOCTOR));
            }
            if (userRepository.findByEmail("neha@hospital.com").isEmpty()) {
                userRepository.save(new User("neha@hospital.com",
                        passwordEncoder.encode("doctor123"),
                        "Dr. Neha Sharma", Role.DOCTOR));
            }
            if (userRepository.findByEmail("rahul@hospital.com").isEmpty()) {
                userRepository.save(new User("rahul@hospital.com",
                        passwordEncoder.encode("doctor123"),
                        "Dr. Rahul Singh", Role.DOCTOR));
            }
        }

        if (patientRepository.count() == 0) {
            patientRepository.save(new Patient("Default Patient", "8880009999", "Male"));
        }
    }
}
