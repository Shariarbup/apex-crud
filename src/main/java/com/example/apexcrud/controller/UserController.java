package com.example.apexcrud.controller;

import com.example.apexcrud.dto.ApiResponse;
import com.example.apexcrud.dto.UserDto;
import com.example.apexcrud.dto.UserFilterCriteria;
import com.example.apexcrud.dto.UserFilterRequestDTO;
import com.example.apexcrud.service.UserService;
import com.example.apexcrud.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "UserController", description = "USER CRUD OPERATION")
@SecurityRequirement(name="Bearer Authentication")
public class UserController {
    @Autowired
    private UserService userService;

    //PUT - update user
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,@Valid @RequestBody UserDto userDto){
        UserDto updateUserDto = userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updateUserDto);
    }
    //Admin
    //DELETE - delete user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
    }

    //GET - get user
    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<UserDto>> getAllusers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping(value = "/users/{userId}", produces = "application/json")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping(value = "/users/filter", produces = "application/json")
    public ResponseEntity<Page<UserDto>> getUsersWithFilter(@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer size,
                                                            @RequestParam(defaultValue = "id") String sortBy,
                                                            @RequestParam(defaultValue = "asc") String sortOrder,
                                                            @ParameterObject UserFilterCriteria userFilterCriteria){

        Pageable pageable = PaginationUtil.buildPageable(page, size, sortBy, sortOrder);
        System.out.println("I am in controller");
        return ResponseEntity.ok(userService.findUsersWithFilter(pageable, userFilterCriteria));
    }

}
