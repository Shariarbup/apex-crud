package com.example.apexcrud.serviceImpl;


import com.example.apexcrud.dto.*;
import com.example.apexcrud.enums.ActiveStatus;
import com.example.apexcrud.exceptions.ApiException;
import com.example.apexcrud.exceptions.ResourceNotFoundException;
import com.example.apexcrud.model.Role;
import com.example.apexcrud.model.User;
import com.example.apexcrud.repositories.RoleRepository;
import com.example.apexcrud.repositories.UserRepository;
import com.example.apexcrud.service.UserService;
import com.example.apexcrud.service.UserSpecification;
import com.example.apexcrud.utils.AppConstants;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserSpecification userSpecification;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        if (userDto.getUserId() != null) {
            var userDb = userRepository.findByUserId(userDto.getUserId().toString()).orElse(null);
            if (userDb != null) {
                throw new ApiException("UserId "+ userDto.getUserId() + " is already exist in DB");
            }
            User user = this.modelMapper.map(userDto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = this.roleRepository.findById(AppConstants.ROLE_USER).get();
            user.getRoles().add(role);
            user.setStatus(ActiveStatus.ACTIVE);
            User newUser = this.userRepository.save(user);
            return this.modelMapper.map(newUser, UserDto.class);
        } else {
            throw new ApiException("UserId cannot be null");
        }

    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User saveUser = userRepository.save(user);
        return userToDto(saveUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = userRepository.findByUserId(userId.toString()).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setDeptmsCode(userDto.getDeptmsCode());
        user.setStatus(userDto.getStatus());
        User userUpdate = userRepository.save(user);
        return userToDto(userUpdate);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findByUserId(userId.toString().trim()).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto deActivateUser(Long userId) {
        User user = userRepository.findByUserId(userId.toString()).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        user.setStatus(ActiveStatus.DEACTIVE);
        User saveUser = userRepository.save(user);
        return this.modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public Page<UserDto> findUsersWithFilter(Pageable pageable, UserFilterCriteria userFilterCriteria) {
        UserFilterRequest userFilterRequest = buildUserFilterRequest(userFilterCriteria);
        Page<User> userPage = userRepository.findAll(userSpecification.getUserSpecification(userFilterRequest.getFilters()), pageable);
        return userPage.map(UserServiceImpl::userToDto);
    }

    @Override
    public String changeUserRole(Long userId, RoleListDto roleListDto) {
        User user = userRepository.findByUserId(userId.toString()).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        if (user != null) {
            Set<Role> roles = user.getRoles();
            roles.clear();
            try {
                List<Role> listOfRole = roleRepository.findAllByRoleIn(roleListDto.getRoleTypeList());
                user.setRoles(new HashSet<>(listOfRole));
                userRepository.save(user);
                return "User role Updated";
            } catch (IllegalArgumentException e) {
                throw new ApiException(e.getMessage());
            }
        } else {
            throw new ResourceNotFoundException("User", "id", userId);
        }

    }

    private User dtoToUser(UserDto userDto){
        User user = modelMapper.map(userDto,User.class);
        return user;
    }

    private static UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(Long.parseLong(user.getUserId()));
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setDeptmsCode(user.getDeptmsCode());
        userDto.setStatus(user.getStatus());
        return userDto;
    }

    Set<Role> userCreationRole() {
        Role role = roleRepository.findById(AppConstants.ROLE_ADMIN).get();
        Role role1 = roleRepository.findById(AppConstants.ROLE_ADMIN).get();
        return Set.of(role, role1);
    }

    private UserFilterRequest buildUserFilterRequest(UserFilterCriteria userFilterCriteria) {
        List<UserFilterRequestDTO> filters = new ArrayList<>();
        if (userFilterCriteria.getUserId() != null) {
            filters.add(UserFilterRequestDTO.builder().field("userId").values(List.of(userFilterCriteria.getUserId().toString())).build());
        }
        if (userFilterCriteria.getUserName() != null) {
            System.out.println("I am here");
            filters.add(UserFilterRequestDTO.builder().field("userName").values(List.of(userFilterCriteria.getUserName())).build());
        }
        if (userFilterCriteria.getEmail() != null) {
            filters.add(UserFilterRequestDTO.builder().field("email").values(List.of(userFilterCriteria.getEmail())).build());
        }
        if (userFilterCriteria.getDesignation() != null) {
            filters.add(UserFilterRequestDTO.builder().field("designation").values(List.of(userFilterCriteria.getDesignation())).build());
        }
        // add here if new field is needed for filtering
        return UserFilterRequest.builder().filters(filters).build();
    }
}
