package com.example.student_management_system.repositiory;

import com.example.student_management_system.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
//    Optional<Manager> findByEmail(String email);
    Optional<Manager> findByUsername(String username);
}