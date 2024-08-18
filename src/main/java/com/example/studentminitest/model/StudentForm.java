package com.example.studentminitest.model;

import org.springframework.web.multipart.MultipartFile;

public class StudentForm {
    private int id;
    private String firstname;
    private String lastname;
    private String dob; // Date of birth
    private String address;
    private double mark; // Assuming mark is a double
    private MultipartFile img; // Assuming img is the file path or URL to the image

    public StudentForm() {
    }

    public StudentForm(int id, String firstname, String lastname, String dob, String address, double mark, MultipartFile img) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.address = address;
        this.mark = mark;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
