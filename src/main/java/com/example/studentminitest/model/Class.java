package com.example.studentminitest.model;

import javax.persistence.*;

@Entity
@Table(name = "Class")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Class(Long id, String name, Student student) {
        this.id = id;
        this.name = name;
        this.student = student;
    }

    public Class() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

