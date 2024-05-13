package com.example.apexcrud.dto;

import com.example.apexcrud.enums.ActiveStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {

    @NotEmpty
    @Size(min=1, max = 50, message = "UserId must be min of 1 chars and max of 50 characters")
    private Long userId;

    @NotEmpty
    @Size(min=1, max = 255, message = "UserName must be min of 1 chars and max of 255 characters")
    private String userName;

    @NotEmpty
    @Size(min=1, max = 255, message = "Password must be min of 1 chars and max of 10 characters")
    private String password;

    @Size(max = 255, message = "Email must be min of 1 chars and max of 255 characters")
    private String email;

    @Size(max = 255, message = "DeptmsCode must be min of 1 chars and max of 255 characters")
    private String deptmsCode;

    private ActiveStatus status;

    private Set<RoleDto> roles = new HashSet<>();
}
