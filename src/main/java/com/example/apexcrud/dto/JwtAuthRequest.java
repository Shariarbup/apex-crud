package com.example.apexcrud.dto;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private Long userId;
    private String password;
}
