package com.example.studentminitest.service;

import com.example.studentminitest.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService extends IGenerateService<Student> {
    Page<Student> findAll(Pageable pageable);

}
