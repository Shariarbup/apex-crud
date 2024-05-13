package com.example.apexcrud.dto;

import com.example.apexcrud.enums.ActiveStatus;
import com.example.apexcrud.model.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDtoResponse {
    private Long userId;

    @NotEmpty
    @Size(min=1, max = 255, message = "UserName must be min of 1 chars and max of 255 characters")
    private String userName;

    @Size(max = 255, message = "Email must be max of 255 characters")
    private String email;

    @Size(max = 255, message = "DeptmsCode must be max of 255 characters")
    private String deptmsCode;

    private ActiveStatus status;

    private Set<Role> roles;
}
