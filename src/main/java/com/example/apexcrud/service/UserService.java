package com.example.apexcrud.service;

import com.example.apexcrud.dto.RoleListDto;
import com.example.apexcrud.dto.UserDto;
import com.example.apexcrud.dto.UserDtoResponse;
import com.example.apexcrud.dto.UserFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDtoResponse registerNewUser(UserDto user);

    UserDto createUser(UserDto user);

    UserDtoResponse updateUser(UserDto user, Long userId);

    UserDtoResponse getUserById(Long userId);

    List<UserDtoResponse> getAllUsers();

    UserDtoResponse deActivateUser(Long userId);

    Page<UserDtoResponse> findUsersWithFilter(Pageable pageable, UserFilterCriteria userFilterCriteria);

    String changeUserRole(Long userId, RoleListDto roleListDto);
}
