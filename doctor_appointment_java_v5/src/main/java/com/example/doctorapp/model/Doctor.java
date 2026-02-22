package com.example.doctorapp.model;

import jakarta.persistence.*;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String phone;
    private String timing;

    @Column(unique = true)
    private String email; // used for doctor login mapping

    public Doctor() {}

    public Doctor(String name, String specialization, String phone, String timing, String email) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.timing = timing;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
