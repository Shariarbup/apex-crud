package com.example.apexcrud.service;

import com.example.apexcrud.dto.RoleListDto;
import com.example.apexcrud.dto.UserDto;
import com.example.apexcrud.dto.UserFilterCriteria;
import com.example.apexcrud.dto.UserFilterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto registerNewUser(UserDto user);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Long userId);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

    Page<UserDto> findUsersWithFilter(Pageable pageable, UserFilterCriteria userFilterCriteria);

    String changeUserRole(Long userId, RoleListDto roleListDto);
}
