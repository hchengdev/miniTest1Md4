package com.example.studentminitest.service;

import com.example.studentminitest.model.Student;

import java.util.List;

public interface IStudentService {
    List<Student> findAll();

    void save(Student student);

    Student findById(int id);

    void remove(int id);
}
