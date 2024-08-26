package com.example.studentminitest.repository;

import com.example.studentminitest.model.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface IClassRepository extends PagingAndSortingRepository<Class, Long> {
    Page<Class> findAll(Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "CALL TruncateClass()", nativeQuery = true)
    void truncateClass();
}
