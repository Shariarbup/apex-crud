package com.example.apexcrud.dto;

import com.example.apexcrud.enums.ActiveStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {

    private Long userId;

    @NotEmpty
    @Size(min=1, max = 255, message = "UserName must be min of 1 chars and max of 255 characters")
    private String userName;

    @NotEmpty
    @Size(min=1, max = 255, message = "Password must be min of 1 chars and max of 10 characters")
    private String password;

    @Size(max = 255, message = "Email must be max of 255 characters")
    private String email;

    @Size(max = 255, message = "DeptmsCode must be max of 255 characters")
    private String deptmsCode;

    private ActiveStatus status;
}
