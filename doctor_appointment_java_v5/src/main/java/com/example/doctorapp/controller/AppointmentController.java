package com.example.doctorapp.controller;

import com.example.doctorapp.model.Appointment;
import com.example.doctorapp.model.Doctor;
import com.example.doctorapp.model.Patient;
import com.example.doctorapp.repository.AppointmentRepository;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.PatientRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 DoctorRepository doctorRepository,
                                 PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        return "appointments";
    }

    @PostMapping
    public String bookAppointment(@RequestParam Long doctorId,
                                  @RequestParam Long patientId,
                                  @RequestParam String date,
                                  @RequestParam String time) {

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        LocalDate d = LocalDate.parse(date);
        LocalTime t = LocalTime.parse(time);

        Appointment appointment = new Appointment(doctor, patient, d, t, "BOOKED");
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/export")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=appointments.csv");

        List<Appointment> appointments = appointmentRepository.findAll();
        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,Doctor,Patient,Date,Time,Status");
            for (Appointment a : appointments) {
                writer.printf("%d,%s,%s,%s,%s,%s%n",
                        a.getId(),
                        a.getDoctor() != null ? a.getDoctor().getName() : "",
                        a.getPatient() != null ? a.getPatient().getName() : "",
                        a.getDate() != null ? a.getDate().toString() : "",
                        a.getTimeSlot() != null ? a.getTimeSlot().toString() : "",
                        a.getStatus() != null ? a.getStatus() : "");
            }
        }
    }
}
