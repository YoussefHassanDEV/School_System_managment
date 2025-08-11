package com.example.student_management_system.service;

import com.example.student_management_system.Enum.Role;
import com.example.student_management_system.model.AppUser;
import com.example.student_management_system.model.Manager;
import com.example.student_management_system.repositiory.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ManagerService {
    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public List<Manager> getManagers() {
        AppUser appUser = new Manager();
        return managerRepository.findAll();
    }

    public Manager getManageById(Long id) {
        return managerRepository.findById(id).orElse(null);
    }

    public Manager addManager(Manager manager) {
        manager.setRole(Role.MANAGER);
        return managerRepository.save(manager);
    }

    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }

    public Manager updateManager(Long id, Manager updatedManager) {
        return managerRepository.findById(id)
                .map(existingManager -> {
                    existingManager.setName(updatedManager.getName());
                    // You can add more fields to update here
                    return managerRepository.save(existingManager);
                })
                .orElseGet(() -> {
                    updatedManager.setId(id);
                    updatedManager.setRole(Role.MANAGER);
                    return managerRepository.save(updatedManager);
                });
    }
}
