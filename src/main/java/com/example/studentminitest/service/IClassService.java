package com.example.studentminitest.service;

import com.example.studentminitest.model.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClassService extends IGenerateService<Class>{
    Page<Class> findAll(Pageable pageable);

}
