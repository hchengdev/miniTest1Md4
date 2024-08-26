package com.example.studentminitest.repository;

import com.example.studentminitest.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "CALL truncateStudent()", nativeQuery = true)
    void truncateStudent();
}
