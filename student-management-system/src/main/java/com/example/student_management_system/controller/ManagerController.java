package com.example.student_management_system.controller;

import com.example.student_management_system.model.Manager;
import com.example.student_management_system.service.ManagerService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {
    private final ManagerService managerService;
    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }
    @GetMapping
    public List<Manager> getAllManagers() {
        return managerService.getManagers();
    }
    @GetMapping("/{id}")
    public Manager getManagersById(@PathVariable Long id) {
        return managerService.getManageById(id);
    }
    @PostMapping
    public Manager createManager(@RequestBody Manager manager){
        return managerService.addManager(manager);
    }
    @PutMapping("/{id}")
    public Manager updateManager(@PathVariable Long id, @RequestBody Manager updatedManager){
        return managerService.updateManager(id, updatedManager);
    }
    @DeleteMapping("/{id}")
    public void deleteManager(@PathVariable Long id){
        managerService.deleteManager(id);
    }
}
