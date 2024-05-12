package com.example.apexcrud.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterCriteria {
    @Parameter(name = "userId")
    private Long userId;
    @Parameter(name = "userName")
    private String userName;
    @Parameter(name = "email")
    private String email;
    @Parameter(name = "designation")
    private String designation;
    @Parameter(name = "deptmsCode")
    private String deptmsCode;
}
