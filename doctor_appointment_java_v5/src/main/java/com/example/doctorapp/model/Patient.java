package com.example.doctorapp.model;

import jakarta.persistence.*;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String gender;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String faceImageBase64; // stores base64 image of face capture

    public Patient() {}

    public Patient(String name, String phone, String gender) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getFaceImageBase64() { return faceImageBase64; }
    public void setFaceImageBase64(String faceImageBase64) { this.faceImageBase64 = faceImageBase64; }
}
