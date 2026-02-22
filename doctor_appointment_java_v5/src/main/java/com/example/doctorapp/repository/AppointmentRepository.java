package com.example.doctorapp.repository;

import com.example.doctorapp.model.Appointment;
import com.example.doctorapp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);
}
