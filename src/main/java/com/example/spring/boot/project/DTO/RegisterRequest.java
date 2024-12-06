package com.example.spring.boot.project.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username ;
    private String password ;
    private String name ;
}
