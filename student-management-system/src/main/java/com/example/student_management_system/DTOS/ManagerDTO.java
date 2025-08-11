package com.example.student_management_system.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDTO {
    private Long id;
    private String name;
    private String username;
}
